# Wizualizacja 2D obróbki płyty na CNC  

Projekt służy do graficznej reprezentacji procesu obróbki płyty. Prosta wizualizacja 2D pozwala na konwersję kodu G-code na grafikę przedstawiającą formatkę oraz wykonywane na niej operacje (wiercenie, cięcie piłą, frezowanie itp.). Dzięki temu ułatwia i przyspiesza pracę przy maszynie CNC.  

Projekt został opracowany podczas pracy w firmie **MEBLART** i jest nadal wykorzystywany w produkcji.  

## Technologie  

- **Java 17** – główny język programowania użyty do stworzenia aplikacji
- **Java AWT & Swing** - biblioteki do obsługi grafiki i interfejsu użytkownika
- **Java I/O** – obsługa plików G-code i operacji wejścia/wyjścia
- **Gradle** - narzędzie do zarządzania zależnościami i budowania aplikacji

## Instalacja  

Aby uruchomić aplikację, pobierz plik `.jar` i uruchom go za pomocą Javy:  

```sh
java -jar Wiercenia-1.0.jar
```  

Wymagana jest zainstalowana **Java 17**.  
