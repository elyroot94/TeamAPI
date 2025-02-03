"# TeamAPI"

## Description

Ce projet est une API REST pour la gestion des équipes et des joueurs. Il permet de créer, lire, mettre à jour et supprimer des équipes et des joueurs.

## Fonctionnalités

- Création, lecture, mise à jour et suppression d'équipes.
- Création, lecture, mise à jour et suppression de joueurs.
- Tri des équipes par nom, acronyme ou budget.
- Pagination des résultats.

## Technologies utilisées

- **Spring Boot** : Framework pour construire l'API REST.
- **Spring Data JPA** : Gestion des données et accès à la base de données.
- **Hibernate** : ORM pour mapper les objets Java aux tables de la base de données.
- **MySQL** : Base de données relationnelle pour l'environnement de production.
- **H2** : Base de données en mémoire pour les tests d'intégration.
- **Docker** : Conteneurisation de l'application et de la base de données.
- **Lombok** : Réduction du code boilerplate (getters, setters, constructeurs, etc.).
- **Swagger** : Documentation interactive de l'API.
- **Spring Boot Test** : Framework pour les tests d'intégration.
- **JUnit** : Framework pour les tests unitaires.
- **Mockito** : Bibliothèque pour les tests unitaires avec des mocks.

* **MapStruct** : Bibliothèque pour le mapping automatique des objets (par exemple, `RequestDto` en `ResponseDto`).

## Prérequis

- Java 17 ou supérieur
- Docker et Docker Compose
- MySQL (optionnel, si vous n'utilisez pas Docker)

```##

1. Clonez le dépôt :


   ```bash
   git clone https://github.com/elyroot94/TeamAPI.git
   cd TeamAPI
```

## Création du fichier `.env` pour la configuration de MySQL

Créez un fichier `.env` et ajoutez-y :

```

MYSQL_DATABASE=mydatabase
MYSQL_USER=root


docker-compose up
```

### **Utilisation de l'API**

Donnez des exemples d'utilisation de l'API.

```markdown
## Utilisation de l'API

### Créer une équipe
```bash
curl -X POST http://localhost:8080/api/teams \
  -H "Content-Type: application/json" \
  -d '{
        "name": "FC Barcelone",
        "acronym": "FCB",
        "budget": 15000000000.00,
        "playerIds":[1,2]
      }'
```

> curl -X GET http://localhost:8080/api/teams?sort=name
