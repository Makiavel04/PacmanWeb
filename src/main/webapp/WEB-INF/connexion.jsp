<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Connexion</title>
    <style>
        body { background-color: #111; color: #fff; font-family: sans-serif; text-align: center; }
        .box { border: 2px solid #FFD700; padding: 20px; border-radius: 8px; width: 300px; margin: 50px auto; }
        input { margin: 10px 0; padding: 8px; width: 90%; }
        input[type="submit"] { background: #FFD700; color: #000; font-weight: bold; cursor: pointer; }
        .erreur { color: #FF5555; font-weight: bold; }
        .btn-inscription { display: inline-block; margin-top: 10px; color: #111; background-color: #fff; padding: 8px 15px; text-decoration: none; border-radius: 4px; font-weight: bold;}
    </style>
</head>
<body>
    <h1>🕹️ Pac-Man Online 🕹️</h1>
    
    <div class="box">
        <h2>Se connecter</h2>
        <p class="erreur">${erreurConnexion}</p>
        
        <form action="connexion" method="post">
            <input type="text" name="pseudo" placeholder="Pseudo" required><br>
            <input type="password" name="motdepasse" placeholder="Mot de passe" required><br>
            <input type="submit" value="Jouer !">
        </form>
        
        <hr style="border-color: #333; margin: 20px 0;">
        <p style="margin-bottom: 5px; font-size: 14px;">Pas encore de compte ?</p>
        <a href="inscription" class="btn-inscription">Créer un compte</a>
    </div>
</body>
</html>