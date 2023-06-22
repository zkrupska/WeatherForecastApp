README

# Weather Forecast App

## 1. Wstęp

Celem  projektu było stworzenie aplikacji internetowej, która umożliwia użytkownikom sprawdzenie aktualnej pogody i prognozy na najbliższe dni dla wybranego miasta. Aplikacja korzysta z OpenWeather API do pobierania danych pogodowych.

## 2. Użyte technologie

Aplikacja została zaimplementowana jako aplikacja internetowa, wykorzystująca protokół HTTPS i uwierzytelnianie użytkowników poprzez rejestrację oraz logowanie. Dane użytkowników są przechowywane w bazie danych MongoDB, która jest hostowana w chmurze AWS.

Backend aplikacji został stworzony przy użyciu frameworka Spring Boot, natomiast frontend opiera się na frameworku Bootstrap. 

## 3. Interfejs użytkownika

Interfejs użytkownika składa się z kilku widoków, w tym:
- Widoki rejestracji i logowania użytkowników
- Widok pomyślnej rejestracji
- Strona powitalna z linkiem do repozytorium projektu
- Widok danych użytkownika
- Widok aktualnej pogody i prognozy dla wybranego miasta

## 4. Interakcja z OpenWeather API

Aplikacja pobiera dane meteorologiczne z API serwisu OpenWeather. Dane są przesyłane w formacie JSON i zawierają informacje o pogodzie na podstawie podanych współrzędnych geograficznych. Jednak dla wygody użytkowników, aplikacja umożliwia wprowadzanie nazwy miejscowości, która jest przekształcana na współrzędne geograficzne za pomocą dedykowanego API OpenWeather. Następnie wysyłane jest zapytanie do API OpenWeather na podstawie otrzymanych współrzędnych, a odpowiedź JSON jest parsowana. Odpowiednie atrybuty są dodawane do modelu i wyświetlane w widoku. Jeśli API nie znajdzie żadnej miejscowości o podanej nazwie, użytkownik zostanie przekierowany z powrotem do strony wyszukiwania.

## 5. Połączenie z bazą danych

Aplikacja komunikuje się z bazą danych MongoDB za pomocą obiektu klasy QuickStart
