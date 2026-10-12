#TP2 : injection de dépendances avec Spring

Ce projet montre comment Spring fabrique et branche les objets à ta place. On n'écrit plus `new DaoImpl()` : on déclare des composants avec des annotations, et Spring s'occupe du reste.

Il y a deux implémentations du même DAO (`DaoImpl` et `DaoImpl2`), ce qui permet de voir comment choisir celle qu'on veut avec `@Qualifier`.

## Ce qui se passe

- `@Component("dao")` et `@Component("dao2")` : Spring enregistre ces deux classes comme des beans, avec ces noms.
- `@Autowired` dans `MetierImpl` : Spring met lui-même un `IDao` dans le champ `dao`.
- `@ComponentScan` dans `Presentation2` : dit à Spring dans quels packages chercher les beans.
- `context.getBean(IMetier.class)` : on récupère le métier déjà prêt à l'emploi.

## Les dépendances (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.1.2</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Résultat avec DaoImpl (avant @Qualifier)

Dans `MetierImpl`, on a seulement `@Autowired` :

```java
@Autowired
private IDao dao;
```

Spring doit choisir entre `dao` et `dao2`. Il regarde le nom du champ (`dao`) et prend le bean qui porte ce nom, donc `DaoImpl`.

```
Résultat = 200.0
```

(100.0 x 2)

#L'execution

<img width="953" height="197" alt="Capture d&#39;écran 2026-10-12 112346" src="https://github.com/user-attachments/assets/c064fbb0-f095-4d29-8993-c17a5d7f72df" />

Avec @Autowired seul, Spring injecte DaoImpl (le bean “dao”, qui correspond au nom du champ) et le programme affiche Résultat = 200.0.

## Résultat avec DaoImpl2 (après @Qualifier)

On ajoute `@Qualifier("dao2")` pour choisir explicitement :

```java
@Autowired
@Qualifier("dao2")
private IDao dao;
```

On relance `Presentation2` :

```
Résultat = 300.0
```

(150.0 x 2)

#L'execution
<img width="1098" height="216" alt="Capture d&#39;écran 2026-10-12 112922" src="https://github.com/user-attachments/assets/794b2fec-1cd7-4a32-9cda-19edda12dd4a" />

Avec @Qualifier("dao2"), Spring injecte DaoImpl2 à la place et le programme affiche Résultat = 300.0.

Pour changer d'implémentation, il suffit de modifier le nom dans `@Qualifier`, sans toucher au reste du code. C'est tout l'intérêt de l'injection de dépendances.

