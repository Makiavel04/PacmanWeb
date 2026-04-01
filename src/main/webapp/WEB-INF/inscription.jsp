<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Inscription</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/main_theme.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/inscription.css' />">
</head>
<body>
    <h1>👻 REJOINDRE LA PARTIE 👻</h1>
    
    <div class="box">
        <h2>NOUVEAU COMPTE</h2>
        
        <p class="erreur"> ${form.erreurs['dao']}</p>
        
        <form action="inscription" method="post">
            <span class="erreur"> ${form.erreurs['pseudo']}</span><input type="text" name="pseudo" placeholder="CHOISIR UN PSEUDO" value="<c:out value="${joueur.pseudo}"/>" required><br>
            <span class="erreur"> ${form.erreurs['motdepasse']}</span><input type="password" name="motdepasse" placeholder="CHOISIR UN MOT DE PASSE" required><br>
            <span class="erreur"> ${form.erreurs['confirmation']}</span><input type="password" name="confirmation" placeholder="CHOISIR UN MOT DE PASSE" required><br>
            <input type="submit" value="CRÉER ET JOUER">
        </form>
        
        <hr style="border-color: #444; margin: 25px 0;">
        <a href="connexion">⬅️ J'ai déjà un compte, retour</a>
    </div>
</body>
</html>