# DISCLAIMER

Sorry for the notes being in Lithuanian.

These notes are extremely rough—essentially just shower thoughts with no proper grammar or style. They simply outline the general direction the presentation followed and roughly reflect what was said during it.

To create the slides, I used [MARP](https://marp.app/) — a tool that allows writing full presentations in `markdown` format.  
The presentation source is in `presentation.md`, with exported versions available as `presentation.pdf` and `presentation.pptx`.

---

#

#

#

#

#

#

---

# RPC

## Kas yra RPC?

Remote procedure call. Politkorektiskai -  architekturinis modelis, kuris leidzia komunikuoti procesams tarp skirtingu workstation. Arba kitaip sakant, workstation A gali callinti workstation B funkcijas, tarsi tai butu ant to paties workstation ir tada under the hood tavo naudojamas rpc implementatio sutvarko visa networkinga ir packetu routinima kad tau nereiketu perdaug stipriai sukti galvos.

RPC yra pati architektura, kuri tiesiog nusako kaip kas turetu veiktu, pvz su C++ mes turim bendra C++ standarta kuris yra mangeniamas ISO, bet yra skirtingu implementationu, kur pvz microsoftas turi savo own C++ compiler, GCC, Clang ir pan. Tai RPC yra tiesiog pats architekturinis modelis ir tada yra daug skirtingu jo implementationu.

Ant popieriaus tai skamba labai nice ir atrodo kad vos ne visa sistema gali rasyti kaip monolitine, taciau jeigu pasiziuret i actual implementation pacio populiariausio rpc frameworko padaryto googlo - gRCP, bent jau mano nuomone pasimatys viso sito dalyko kompleksikumas

## gRCP

Tai yra RPC implementationas, kuri pradejo developinti googlas kaip open-source implementation ir mazdaug nuo 2017 yra developinamas kitos organizacijos.

gRCP veikia ant HTTP/2 ir TCP. Kas essentially reiskia kad tai yra reliable ir stable connectionas, bet uz tai moki ciu ciut didesniu latency, tai pvz kokiu competittive multiplayer gamu serveriu ant sito frameworko neparasysi.

Dabar truputi apie pati gRCP, kaip ji setupinti ir kodinti. Perspeju, bandysiu biski plestis i technical details, tai jei judesiu pergreit ar turesit kokiu klausimu, geriau neklauskit, nes tikrai neatsakysiu.

Pabandysiu parodyti vos ne labai basic hello world example su gRCP ir python. Tai pradziai reikia apsirasyti toki `.proto` failiuka kuris gyvena pacio projekto root'e. Essentiallu tai yra protocolo bufferis, kuris definina tavo visos siunciamos datos shape ir methodos kuriuos tu nori exposinti.  Visas sitas `.proto` failas ir jo syntaxe vadinasi Interface Definition Language (IDL)

```.proto
syntax = "proto3";
package calc;

// Message: two numbers
message Operands {
  double a = 1;
  double b = 2;
}

// Message: one result
message Result {
  double value = 1;
}

// Service: one unary RPC
service Calculator {
  rpc Add (Operands) returns (Result);
}
```

Tai `Operands` essentially nurodai argumentus kuriuos noresi passinti i funkcija. Tie `a = 1` ir `b = 2` nera kad tu definini kam ta data yra lygi, cia tiesiog nurodai argumentu seka, kuri poto under the hood bus naudojama binarinei serialicaijai, essentially tiesiog visa data kuria tu siusi susiconvertinti i binary, ir tada kada ji atkeliauja suconvertinti atgal i language specific objects. Truputi confusing, bet reik prisiminti kad sitas file format buvo darytas googlo kai mes dar net nebuvom gime, tai kinda paaiskina complexity. Jo, o `Result` definina kiek mes variablu noresim returninti, o pats `service` jau nurodo visa metodo struktura kuri mes exposinsim.

Tada turint visa sita `.proto` file, naudojant pythona ir jo specific pluginus per CLI labai graziai galim susikonvertinti tokius `stub` files.

CLI:
```bash
python -m grpc_tools.protoc \
  --proto_path=. \
  --python_out=./gen \
  --grpc_python_out=./gen \
  calculator.proto
```

Kaip atrodo project tree:

```bash
gRPC/
├── calculator.proto
├── client.py # Sukursim veliau
├── server.py # Sukursim veliau
└── gen/
    ├── calculator_pb2.py
    └── calculator_pb2_grpc.py
```

I pati `stub` failu vidu tikrai neisim, nes ten jau perdaug reiketu veltis i visus technical details. Essentially ten tupi logika, kuri pajema visa data ir graziai sukonvertina viska i binary formata kad jau tai butu galima siusti per HTTP/2 per TCP ir tada graziai unpackint ta binary formata kad jis nukeliautu kur reikia, excetutintu tas funkcijas kurias reikia ir pasiimtu ju data ir grazintu atgal.

Situose stub failiukuose egzsituoja funkcijos, kurias mes naudosim is client.py is server.py kad apsirasyt visa logika.

Dabar galim keliaut i `server` ir `client` implementation.

```py
import grpc
import gen.calculator_pb2 as pb2
import gen.calculator_pb2_grpc as pb2_grpc

def run():
    channel = grpc.insecure_channel('localhost:50051')
    stub = pb2_grpc.CalculatorStub(channel)
    req = pb2.Operands(a=3, b=5)
    res = stub.Add(req)
    print(f"3 + 5 = {res.value}")

if __name__ == "__main__":
    run()
```

Tai pirma susikuriam `channel` kur production environemnte nurodytumem kito workstation IP ir porta.

Pasiemam `stub` objekta. Visi sitie methodai jau automatiskai uz mus buvo sukurti kai paleidom ta CLI programa, kaip is sakiau i ju technical details neisiu, tiesiog apzvelginejam kaip viskas atrodytu is developer stand-point.

Susikuriam `req` variable, kuris veikia kaip argumentai. Vel naudojam funkcijas kurios buvo sukurtos tos CLI komandos, under the hood jie situos argumentus pavercia i binary formata kuris jau bus ready siusti per networka.

Nu ir lastly pasiimam `res` variable, callinam musu stuba kuris yra susiconnectines su kitu workstationu ir jis ant to workstationo pacallino Add functiona ir passino argumentus. ir tada rezultata grazino i musu workstationa.

Dabar server implementation: 

```py
import grpc
from concurrent import futures
import gen.calculator_pb2 as pb2
import gen.calculator_pb2_grpc as pb2_grpc

class CalculatorServicer(pb2_grpc.CalculatorServicer):
    def Add(self, request, context):
        return pb2.Result(value = request.a + request.b)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))
    pb2_grpc.add_CalculatorServicer_to_server(CalculatorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server listening on port 50051")
    server.wait_for_termination()

if __name__ == "__main__":
    serve()

```

Susikuriam dedikuota `CalculatorServicer` class.

Susikuriam function `Add`, kuris kaip argumenta priema `request`, tie kiti kaip `self` ir `context` yra python specific.

Nu ir galu gale returninam resultata i clienta.

Nu ir `serve` functionas yra tiesiog boilerplate kodas kuris uzregistruoja ta methoda kad jis butu matomas networke.

Tai va, mano akimis cia tikrai nera tarsi tu kodintum viska ant vieno workstationo, tikrai yra labai daug overheado, complexiskumo ir is developer point of view tikrai turesi daug skausmo. Nu bet cia yra tiktais googlo RPC implementationas, veliau pakalbesiu apie truputi labiau praktinius RPC panaudojimus ir jo implementacija zaidimuose.

## RPC vs API.

Tai va, jeigu butumet protingi berniukai ar mergaites, dabar paklaustumet, "O tai jeigu visa sita architektura yra tokia kompleksika, kodel tiesiog nenaudojus paprastesniu API callsu, kaip RESTful ar paprastu HTTP requestu".

Labai geras klausimas, aciu kad paklauset.

Tai essentially RPC ir yra API callsai, tiksliau RPC yra API subsetas, nes i API definitiona ieina ir RPC.

Trumpai ir drutai, tai RPC naudojam tada, kada mum nereikia tradicino client-server architecture, o labiau tokiem distributed systems, kur keli workstationai sharina identical logika, ir kad jiem butu paprasta apsikeisti info. Plius ant virsaus tokiem popudziam naudojant RPC architektura, truputi sumazeja latency negu tarkim RESTful.

## RPC in video games. (Godot specific implementation).

Dabar mano manymu labai geras RPC implementationas ir pavyzdis, kur actually pasimato visa RPC architekturos galia.

Siam pavyzdiui naudosiu Godot specific implementationa ir Godot specific language.

GDScripto syntaxe yra labai panasi i pythona tai manau tikrai suprasit kai prieisim technical implementacija.

Visa zaidimo architektura kuria rodysiu yra peer-to-peer, tai pats zaidimo kodas per clients yra identical. Naudojant RPC galima rasyti ir pilna client-server architecture, kuri net budu e-sports ready multiplayer. Bet tiesiog for simplicity parodysiu paprasta p2p implementation.

Tai pradziai turim apzvelgti godot specific `@rpc` anotation, tu gali exposinti betkokia funkcija kaip RPC calla, kas reiskia kad betkoks kitas klientas prisijunges prie taves betkada gales iskviesti ta funkcija ant tavo kliento.

```swift
@rpc(mode, sync, transfer_mode)
func sample_func()
```

`mode`:
- `"authority"` tiktais serveris, arba musu atveju tiktais tas kas hostina p2p connectiona gales kviesti ta funkcija.
- `"any_peer"` bet kas is tinklo gali kviesti ta funkcija.

`sync`:
- `"call_remote"`: jei kaip klientas callinsi sita funkcija, jinai pasicallins ant visu prisijungsiu clientu, bet ant taves ne.
- `"call_local"`: pasicallins ir ant taves.

`transfer_mode`:
- `"unreliable"`: Godot'o RPC implementations yra rasytas ant UDP protkolo, kuris by default neturi 3way handshake kad patikrinti ar paketas kuris buvo issiustas actually nukeliavo, tai siuo atveju mes sakom, kad galim keepint ta default behavior ir nenaudot 3way handshako, tai ypac naudinga kodinant high paste multiplayer games, kur tarkim tickrate yra 128 ir tau reikia issiusti ir gauti 128packetus per sekunde, tai dar ant virsaus apsikrauti ir tikrinti ar tie packetai sukeliavo butu papildomas latency o jei tarp tu 128packetu keli pasiklydo tikrai jau jokio pajutimo neturesi.
- `"reliable"`: Va cia jau actually naudojam 3way handshake patikrinima ar packetas nukeliavo ar ne, useful kai darom kokius UI changes kurie ten ivyksta po kelis kartus i minute ir pnasiai, kai for sure norim kad change reflectintusi per visus clients.

Sample implementation.

```swift
@rpc("any_peer", "reliable", "call_local")
func hello_world():
    print("Hello, world!")

func process():
	if button_pressed:
		hello_world.rpc()
```

Uzregistruojam funkcija `hello_world` kaip rpc funkcija.

Ir tada klausom ar buvo paspaustas mygtukas ir nesvarbu kuris klientas paspaude ta mygtuka, i konsole bus isvestas "Hello world" ant visu prisijungusiu klientu.

Tai va, zinant visus Godoto implementationo basicus galim nertris i truputi detalesni pavyzdi. Tai pabandysiu parodyti kaip paciame kode atrodytu connectiono sudarymas, jo uzmezgimas, prisijungimas, playerio atspawninimas i pasauli, jo movementas, ir to movemento syncinimas per visus clientus.

`NetworkManager.gd` scriptas kuris butu uzloadinamas iskarto kai zaidimas yra paleidziamas.

```swift
extends Node
@export var port: int = 12345
@export var max_clients: int = 8
@export var is_server: bool = false

func _ready():
	var peer = ENetMultiplayerPeer.new()
	if is_server:
		peer.create_server(port, max_clients)
	else:
		peer.create_client("127.0.0.1", port)
	multiplayer.multiplayer_peer = peer
	multiplayer.peer_connected.connect(_on_peer_connected)
	spawn_player()

func _on_peer_connected(id: int):
	spawn_player.rpc()

@rpc("call_local", "reliable")
func spawn_player():
	var p = preload("res://Player.tscn").instantiate()
	add_child(p)
```

```swift
extends CharacterBody2D
@export var speed := 200

func _physics_process(delta):
    if is_multiplayer_authority():
        var inp = Vector2(
            Input.get_action_strength("ui_right") - Input.get_action_strength("ui_left"),
            Input.get_action_strength("ui_down")  - Input.get_action_strength("ui_up")
        )
        velocity = inp.normalized() * speed
        move_and_slide()
        sync_state.rpc(global_position)

@rpc("any_peer", "unreliable", "call_remote")
func sync_state(pos:Vector2):
    global_position = pos;
```
