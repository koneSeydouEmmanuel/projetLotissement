## Template projet CI/CD : Tomcat

URL du projet template : https://git.smile.ci/template/tomcat.git

### Documentation

##### Cloner le template :

```bash
git clone git@git.smile.ci:template/tomcat.git <nom_projet>
cd <nom_projet>
```

##### Créer les différentes branches du projet :
Par convention, nous utilisons deux branches supplémentaires en plus de la branche `master` créee par défaut : `staging`, `develop`
```bash
git checkout -b staging
git push -u origin staging

git checkout -b develop
git push -u origin develop
```

##### Continuer de recevoir le mises à jour de templates
Une fois le template cloné, vous pouvez continuer à recevoir ses mises à jour en conservant son lien.
```bash
git remote rename origin upstream
#Ensuite si vous souhaitez mettre à jour plus tard le template,
git pull upstream master
```

##### Ajoutez le lien du projet crée sur Gitlab
Une fois le projet crée sur git.smile.ci, une url se terminant par .git vous sera fournit sous ce modèle `git@git.smile.ci:<username>/<nom-projet>.git`.

```bash
git remote add origin <lien-de-votre-projet-sur-gitlab>
```

##### Script d'initialisation
Toutes les opérations listées plus haut peuvent être effectuées à l'aide du script `init.sh` que vous trouverez à la racine du template
```bash
./init.sh --url=https://git.smile.ci/projet.git
```


### Variables Projets

Les variables projets sont à définir dans **Settings** > **CI / CD** > **Secret Variables** :

Variable | Default value | Description
---------|---------------|-----------------------
`APP_NAME` | _obligatoire_ | Nom d'application
`APP_URL_PRD` | _Optionnel_  | URL de l'application sur l'environnement de production
`APP_URL_DEV` | _Optionnel_ | URL de l'application sur l'environnement de développement
`APP_URL_STG` | _Optionnel_ | URL de l'application sur l'environnement de pré-production


### Inventaire Ansible

Les groupes Ansible sont à renseigner en ajoutant les serveurs sur lesquels l'application doit être déployée dans le fichier **ansible/inventory/hosts** du projet.

  * Serveurs sur lesquels les sources de l'application seront déposées :

Groupe Ansible | Description
---------------|--------------
`develop` | Serveurs de développement
`staging` | Serveurs de pré-production
`production` | Serveurs de production

### Initier le déploiement

#### Les tags
Git possède une fonctionnalité d'étiquettage qui permet de marquer des points clé au cours du développement. Il est surtout utilisé pour créer des numéro de version (1.0, v2.0) comme on en connait beaucoup en informatique. Nous allons utiliser cette fonctionnalité pour initier notre pipeline. Les tags sont liés à des commits ce qui signifie qu'il sera lié au dernier commit effectué.

pour plus d'informations sur les tags suivez le lien suivant [Les bases de l'étiquettage](https://git-scm.com/book/fr/v1/Les-bases-de-Git-%C3%89tiquetage)

#### Créer un tag 
```bash
git tag -a v1.4 -m "my version 1.4"
```

#### Pusher un tag 
```bash
git push origin v1.4
```
#### Nomenclature
Pour déclencher sur une pipeline, il faut créer et pusher un tag avec la nomenclature obligatoire suivante:

Nom | Environnement
---------------|--------------
`dev-*` | Serveurs de développement
`stg-*` | Serveurs de pré-production
`prod-*` | Serveurs de production

le `*` étant un wildcard, il vous permet d'ajouter ce que vous voulez comme numéro de version, `dev-1.0`, `stg-1.0`, `prod-1.0` sont des valeurs possibles.


### Déploiements des sources

Les sources du projet sont à déposer dans le repertoire **src**

