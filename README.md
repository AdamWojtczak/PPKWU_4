# PPKWU_4
### Panoramafirm.pl vCard scrapper
API służące do pobierania plików .vcf generowanych na podstawie wyszukań na stronie ``panoramafirm.pl``
### Działanie programu
Żeby skorzystać z API należy ustawić zapytanie do endpointa ``/getvcard`` podając parametry name i index.
Parametr index służy do określenia, której z firm wizytówke chcemy otrzymać jako plik do pobrania. Nie udało mi się
zaimplementować formularza który wyświetlałby wszystkie wyniki. Dlatego mimo że pobieram wszystkie wyniki to wymagam 
wskazania pozycji w ten sposób.
### Przykładowe wywołania
Dla parametrów name = hydraulik i index = 10:
```
http://localhost:8080/getvcard?name=hydraulik&index=10
```
pobiera plik vCard dla profesji i indeksu wskazanego w parametrach: name i index.

### Przykładowe wywołanie:
Profesja: hydraulik
index: 10

```
BEGIN:VCARD
VERSION:4.0
ORG:Gamar Marek Garliński
TEL:601746608
ADR:ul. Daszyńskiego 10 F/3 56-400
EMAIL:marekgarlinski@wp.pl
URL:http://www.gamar.olesnica.pl
END:VCARD
```


### Biblioteki zewnętrzne
* Przy tworzeniu pliku w formacie .vcs używam własnego kodu
* Do skrapowania wyników wyszukiwań z panoramafirm.pl korzystam z biblioteki [JSoup](https://jsoup.org/)

