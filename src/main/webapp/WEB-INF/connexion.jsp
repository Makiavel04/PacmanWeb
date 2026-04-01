<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Connexion</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/main_theme.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/connexion.css' />">
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