<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Connexion</title>
    <style>
        body { background-color: #111; color: #fff; font-family: 'Courier New', Courier, monospace; text-align: center; margin-top: 50px;}
        .box { border: 3px solid #FFD700; padding: 30px; border-radius: 10px; width: 320px; margin: 0 auto; background-color: #222; box-shadow: 0 0 15px #FFD700;}
        h1 { color: #FFD700; text-shadow: 2px 2px #000; }
        input[type="text"], input[type="password"] { margin: 10px 0; padding: 10px; width: 90%; border: 2px solid #555; border-radius: 5px; background: #000; color: #fff; font-family: 'Courier New';}
        input[type="submit"] { background: #FFD700; color: #000; font-weight: bold; cursor: pointer; padding: 10px; width: 95%; border: none; border-radius: 5px; margin-top: 15px; font-size: 16px; text-transform: uppercase;}
        input[type="submit"]:hover { background: #FFC107; transform: scale(1.02); }
        .erreur { color: #FF5555; font-weight: bold; margin-bottom: 15px; font-size: 14px;}
        .btn-inscription { display: inline-block; margin-top: 15px; color: #111; background-color: #00FF00; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;}
        .btn-inscription:hover { background-color: #00CC00; }
    </style>
</head>
<body>
    <h1>🕹️ PAC-MAN ONLINE 🕹️</h1> 
    
    <div class="box">
        <h2>IDENTIFICATION</h2>
        
        <c:if test="${not empty erreurConnexion}">
            <p class="erreur">${form.erreurs['dao']}</p>
        </c:if>
        
        <form action="connexion" method="post">
            <span class="erreur"> ${form.erreurs['pseudo']}</span><input type="text" name="pseudo" placeholder="VOTRE PSEUDO" value="<c:out value="${joueur.pseudo}"/>" required><br>
            <span class="erreur"> ${form.erreurs['motdepasse']}</span><input type="password" name="motdepasse" placeholder="MOT DE PASSE" required><br>
            <input type="submit" value="START GAME">
        </form>
        
        <hr style="border-color: #444; margin: 25px 0;">
        <p style="margin-bottom: 10px; font-size: 14px; color: #aaa;">Nouveau joueur ?</p>
        <a href="inscription" class="btn-inscription">INSERT COIN (Créer un compte)</a>
    </div>
</body>
</html>