# Regelungen zur Abgabe

Hier das bitte InteliJ Project als zip-File hochladen. Es ist ein ausgedrucktes Protokoll abzugeben. Spätestens am Montag 19.11.2018 in mein Fach. Es gelten die üblichen Regelungen

Zusätzliche Anforderungen:

- Es sind immer Gruppen mit jeweils zwei Personen zu bilden
- Für die Note 1 (13-15 Punkte) sind Erweiterungen erforderlich. (GUI etc)
- Das Projekt enthält unter dem Unterordner &quot;docs&quot; das Protokoll als PDF.

# Conways Game of Life

Es soll Conways Game of Life simuliert werden. Schreiben Sie dazu ein passendes Programm in JAVA. Die Regeln entnehmen Sie bitte dem angegebenen Wikipedia Artikel.

## **Die Spielregeln** **[**[**Zum Artikel**](https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens)**]**

Die Folgegeneration wird für alle Zellen gleichzeitig berechnet und ersetzt die aktuelle [Generation](http://de.wikipedia.org/wiki/Generation). Der Zustand einer Zelle, _lebendig_ oder _tot_, in der Folgegeneration hängt nur vom Zustand der acht Nachbarzellen dieser Zelle in der aktuellen Generation ab.

Die von Conway zu Anfang verwendeten Regeln sind:

- Eine tote Zelle mit genau drei lebenden Nachbarn wird in der Folgegeneration neu geboren.

- Lebende Zellen mit weniger als zwei lebenden Nachbarn sterben in der Folgegeneration an Einsamkeit.

- Eine lebende Zelle mit zwei oder drei lebenden Nachbarn bleibt in der Folgegeneration am Leben.

- Lebende Zellen mit mehr als drei lebenden Nachbarn sterben in der Folgegeneration an Überbevölkerung.


In einem ersten Schritt wählen Sie ein Spielfeld der Größe 40x40. Legen Sie die die Größe des Spielfeldes mit den Konstanten X\_SIZE und Y\_SIZE fest. Das Spielfeld soll durch ein int-Array der Größe X\_SIZE x Y\_SIZE realisiert werden.

Die linke untere Ecke des Spielfeldes stellt den Koordinatenursprung mit den Koordinaten (x=0, y=0) dar. Die Koordinaten werden immer angegeben in der Reihenfolge (x,y).

Beim Spielfeld handelt es sich um eine sogenannte Donut-World. Das heißt der linke Rand grenzt an den rechten Rand an und der untere Rand an den oberen. Dies ist auch bei der Berechnung der Anzahl der Nachbarn zu berücksichtigen.

In der ersten Version des Programms werden die einzelnen Runden auf der Konsole ausgegegen. Dazu dient die Methode showGrid().

In einem zweiten Schritt sollen die Bildschirmausgaben mit Hilfe der Klasse VisualGameOfLife grafisch dargestellt werden. Dazu diese Klasse einfach ihrem Projekt hinzufügen. Die Verwendung dieser Klasse geschieht im wesentlichen durch die refresh-Methode. Schauen Sie sich bitte die main-Methode von VisualGrid an.

Zum Testen ihrer Klasse sollten Sie zwei Testfälle vorbereiten.

**Testfall 1 - Glider**

Erzeugen Sie eine Struktur, die über den gesamten Bildschirm wandert. Auf diese Weise testen Sie auch die Eigenschaften ihrer Feldgrenzen.

**Testfall 2 - Muster mit begrenzter Lebensdauer**

In dem entsprechenden Wikipedia Artikel ist ein Muster erwähnt, das z.B. innerhalb von 54 Generationen eine leere Welt erzeugen. Dies ist eine guter Testfall, da sie damit gut die Logik ihres Programmes überprüfen können.
