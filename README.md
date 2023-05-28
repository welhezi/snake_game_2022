# Java Snake game
## description 
ce projet maven est Java Swing Application + Spring framework,il représente jeu de serpents qui autorise plusieurs joueurs de jouer ensembles

## comment démarer le projet 
pour lancer l'application il faut executer la classe "Runner" et faire un balayage avec la sourie sur l'interface pour apparaitre les choix

## comment jouer ce jeu 
chaque serpent est contrôlé par les touches spécifiés par le programmeur. 
Initialement, le serpent a 4 articulations.
Si on va lancer le jeu en local , il se déclenche immédiatement après spécification de nombre de serpents préféré 
Sinon ( plusieurs joueurs sur la même scène de jeu ) : un premier va lancer la scène du jeu en cliquant sur le bouton 'placer le serveur' et choisir le nombre de joueurs qui vont rejoindre le jeu . Puis, tous les autres joueurs peuvent rejoindre le jeu en cliquant sur 'rejoignez le jeu' 
Le programme attend que les joueurs rejoignent la scène pour déclencher immédiatement le jeu 
Si un joueur quitte le jeu, un message va être  affiché au joueurs ' joueur num …. Fini le jeu ' ou 'connexion au serveur a été perdu' et on finit le jeu
Si le jeu est fini, nous affichons Game Over comme message dans le milieu de la commission
Si un joueur veut rejoindre une réunion qui n'existe pas , le programme affiche un message ' RMI signale une erreur de connexion au serveur '

## le protocole utilisé pour la destribution du jeu
Remote method invocation, plus connu sous l'acronyme RMI est une interface de programmation (API) pour le langage Java

## infos supplémentaires
pour plus d'information merci de rejoindre la présentation "snake_game_presentation" la ou vous pouvez trouver une explication de notre méthode de travail
au lieu de faire un rapport je trouve mieux faire un présentation qui explique mon travail 
