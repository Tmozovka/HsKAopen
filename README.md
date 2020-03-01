# Projekttitel

HsKAopen ist eine Android-Application, die für die Hochschule Karlsruhe Technik und Wirtschaft für eine einfache WLAN-Verbindung entwickelt wurde.

## Installieren

Sie müssen [Android Studio installieren](https://developer.android.com/studio/install), um die Software zu auf Ihrem Computer zu benutzen.
In Android Studio kann der Projekt importiert werden (File -> New -> Import Project) und für die Test- und Entwicklungszwecke verwendet werden. 

Sie können die App [in der Emulator ausführen](https://developer.android.com/studio/run/emulator) oder [auf Ihrem Handy](https://developer.android.com/studio/run/device). In Windows dafür muss noch ein [passender Treiber installiert werden](https://developer.android.com/studio/run/oem-usb). 

## Verwendung

Die HsKAopen Application ermöglicht es die MAC-Adresse von Handy an der Server von Hochschule zu senden, um die WLAN-Verbindung mit Internetdienste außerhalb von Hochschulnetz ohne Proxy einzurichten. 

Benutzer muss eigene IZ-Benutzername und IZ-Passwort eingeben, um die MAC-Adresse zu senden. 

![Connect to HsKAopen](images_for_readme/gif_use_app.gif)

In der Applikation werden auch die konkrete Schritte für die HsKAopen unter dem Abschnitt "App requirements" beschrieben, um für Benutzer klar zu machen, was die Applikation macht. 

Im den Abschnitt wird auch beschrieben, wie der Benutzer die fehlgeschlagene Verbindung reparieren kann Z.B Sicherheitszertifikat installieren.

![Sicherheitszertifikat installieren](images_for_readme/install_certificate.gif)

An der Server von Hochschule darf ein Benutzer nur 3 Geräte registrieren. Falls der Benutzer vierte Gerät regestrieren will, bekommt er ein Meldung, dass der vierte Gerät registriert wird und der erste gelöscht wird.

![Registrationsmeldung](images_for_readme/allow_just_3_devices.gif)

## System

### Activities 

Die HsKAopen Application beinhaltet drei Activities(Bildschirme, mit der Benutzer interagiert):

#### MainActivity

Activity, bei der folgendes registriert wird:
* "OnClickListener" für TextView und Warnungsbild, der bei anklicken Benutzer zur "App requirements"-Activity überleitet. 
* Felder für Benutzername- und Passworteingaben. 
* "SetOnClickListener" für "Connect"-Button. Unter der Listner sind viele Hintergrundprozesse versteckt:
    1. Überprüfung, ob Benutzername und Passwort richtig angegeben wird 
    (Die Felder müssen nicht leer seind. Benutzername muss vier Buchsatben und vier Zahlen beinhalten. Z.b abcd1234).
    2. RestApiConnector regestrieren.
    3. An der RestApiConnector Anfrage für die HsKAOpen Verbindung abschicken. 

        Zuerst werden die gespecherte auf dem Server von Benutzer MAC-Adressen abgefragt und in einem TextView in Hintergrund geschrieben.
        Wenn den Text in der TextView verändert wird, wird es überprüft, ob der MAC-Adresse im Text beinhaltet ist( nämlich ob die MAC-Adresse vom Handy schon auf dem Server gespeichert ist). 

        Falls nein, wird zu den Text in der TextView die MAC-Adresse vom Handy hinzugefügt. Danach wird es überprüft, ob die in der Vergangenheit abgeschickte MAC-Adressen (sie sind in Handy unter "SharedPreferences" gespeichert) in Text gespeichert sind. Falls ja, werden sie aus dem Handyspeciher und Text gelöscht. Es kann passieren, wenn der Benutzer "MAC-Randomization" nicht abgeschaltet hat.
        Im den Fall wird bei jeder HsKAopen verbindung neue MAC-Adresse generiert und folglich muss die Verbindung mit HsKAopen neu eingerichtet werden. 

        Falls die MAC-Adresse erfolgreich an Server abgeschickt wird, wird in MainActivity der Wert "readyToConnect" auf "True". 
        In MainActivity wird noch ein OnChangeListner für den TextView in Hintergrund gesetzt. Wenn Text in TextView verändert würde und "readyToConnect == True", wird der Benutzer zur Activity "ConnectToHskaWlan" überleitet. 
        Die Überleitung bedeutet, dass die MAC-Adresse auf dem Server erfolgreich abgespeichert ist und Benutzer muss noch nur die Verbindung mit Hska-8021x anpassen. 
    4. Nach dem "Connect"-Button angeklickt wird, wird sie für 2500 Milisekunden inaktiviert, um die Mehrere REST-Anfrage zu vermeiden, wenn die erste Anfrage bearbeitet wird.

#### ConnectToHskaWlan

Die Activity wird aufgerufen, falls die MAC-Adresse erfolgreich an Server abgeschickt wurde. 
Am nächsten muss Benutzer die Benutzername in der Hska-8021x anpassen und die HsKAopen wird funktionieren.
Die ausführliche Einleitung wird in der Activity angegeben. 

#### WarningRequirements

In der Activity werden die Schritte für HsKAopen-Verbindung geklärt und wird beschribenen, wie der Benutzer fehlerhafte Verbindung reparieren kann. 

Unter der Abschnitt "Disable MAC Randomisation" wird nicht nur erklärt, wie man MAC-Randomisation deaktivieren kann, 
sonder auch wieso man das machen muss und was MAC-Randomisation ist. 

Unter der Abschnitt "Fix error issues with HsKA-8021x Connection" ist ausführlich beschrieben, wie man sich mit HsKA-8021x WLAN richtig verbinden kann. Es beinhaltet auch die Installation von Sicherheitszertifikat.

## Ausblick und Ideen

Die Applikation ist betriebsbereit uns muss nur für die breite Massen veröffentlicht werden. 
Sie könnte im Namen von "HsKAmpus" in AppStore publiziert werden. 

## Autoren

* **Tatsiana Mazouka** - [tmozovka](https://github.com/tmozovka)

## Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert. Weitere Informationen finden Sie in der Datei [LICENSE.md] (LICENSE.md)

## Danksagung

* Vielen Dank an **Prof. Dr. rer. nat. Oliver P. Waldhorst** und **Prof. Dr.-Ing. Holger Vogelsang** für die Betreuung. 