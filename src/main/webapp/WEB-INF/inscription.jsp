<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Inscription</title>
    <style>
        body { background-color: #111; color: #fff; font-family: 'Courier New', Courier, monospace; text-align: center; margin-top: 50px;}
        .box { border: 3px solid #00FF00; padding: 30px; border-radius: 10px; width: 320px; margin: 0 auto; background-color: #222; box-shadow: 0 0 15px #00FF00;}
        h1 { color: #00FF00; text-shadow: 2px 2px #000; }
        input[type="text"], input[type="password"] { margin: 10px 0; padding: 10px; width: 90%; border: 2px solid #555; border-radius: 5px; background: #000; color: #fff; font-family: 'Courier New';}
        input[type="submit"] { background: #00FF00; color: #000; font-weight: bold; cursor: pointer; padding: 10px; width: 95%; border: none; border-radius: 5px; margin-top: 15px; font-size: 16px; text-transform: uppercase;}
        input[type="submit"]:hover { background: #00CC00; transform: scale(1.02); }
        .erreur { color: #FF5555; font-weight: bold; margin-bottom: 15px; }
        a { color: #FFD700; text-decoration: none; font-size: 14px; display: inline-block; margin-top: 15px;}
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>👻 REJOINDRE LA PARTIE 👻</h1>
    
    <div class="box">
        <h2>NOUVEAU COMPTE</h2>
        
        <c:if test="${not empty erreurInscription}">
            <p class="erreur">❌ ${erreurInscription}</p>
        </c:if>
        
        <form action="inscription" method="post">
            <input type="text" name="nouveauPseudo" placeholder="CHOISIR UN PSEUDO" required><br>
            <input type="password" name="nouveauMdp" placeholder="CHOISIR UN MOT DE PASSE" required><br>
            <input type="submit" value="CRÉER ET JOUER">
        </form>
        
        <hr style="border-color: #444; margin: 25px 0;">
        <a href="connexion">⬅️ J'ai déjà un compte, retour</a>
    </div>
</body>
</html>