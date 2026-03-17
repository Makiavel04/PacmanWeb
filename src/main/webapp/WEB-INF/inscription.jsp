<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Inscription</title>
    <style>
        body { background-color: #111; color: #fff; font-family: sans-serif; text-align: center; }
        .box { border: 2px solid #FFD700; padding: 20px; border-radius: 8px; width: 300px; margin: 50px auto; }
        input { margin: 10px 0; padding: 8px; width: 90%; }
        input[type="submit"] { background: #FFD700; color: #000; font-weight: bold; cursor: pointer; }
        .erreur { color: #FF5555; font-weight: bold; }
        a { color: #FFD700; text-decoration: none; font-size: 14px; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>👻 Rejoindre la partie 👻</h1>
    
    <div class="box">
        <h2>Créer un compte</h2>
        <p class="erreur">${erreurInscription}</p>
        
        <form action="inscription" method="post">
            <input type="text" name="nouveauPseudo" placeholder="Choisis un Pseudo" required><br>
            <input type="password" name="nouveauMdp" placeholder="Choisis un Mot de passe" required><br>
            <input type="submit" value="M'inscrire et Jouer !">
        </form>
        
        <hr style="border-color: #333; margin: 20px 0;">
        <a href="connexion">⬅️ J'ai déjà un compte, me connecter</a>
    </div>
</body>
</html>