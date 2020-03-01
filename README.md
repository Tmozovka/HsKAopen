# Projekttitel

HsKAopen ist eine Android-Application, die für die Hochschule Karlsruhe Technik und Wirtschaft für eine bequeme Wi-Fi-Verbindung entwickelt wurde.

## Installieren

Sie müssen [Android Studio installieren](https://developer.android.com/studio/install), um die Software zu auf Ihrem Computer zu benutzen.

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

* **MainActivity**

Activity, bei der folgendes registriert wird:
* "OnClickListener" für TextView und Warnungsbild, der bei anklicken Benutzer zur "App requirements"-Activity überleitet. 
* Felder für Benutzername- und Passworteingaben. 
* "SetOnClickListener" für "Connect"-Button. Unter der Listner ist viel Hintergrundprozesse versteckt:
..* lala


## Autoren

* **Tatsiana Mazouka** - [tmozovka] (https://github.com/tmozovka)

## Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert. Weitere Informationen finden Sie in der Datei [LICENSE.md] (LICENSE.md)

## Danksagung

* Vielen Dank an Prof. Dr. rer. nat. Oliver P. Waldhorst und Prof. Dr.-Ing. Holger Vogelsang für die Betreuung. 