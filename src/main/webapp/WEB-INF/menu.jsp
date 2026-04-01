<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Dashboard</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/main_theme.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css' />">
</head>
<body>

    <div class="header">
        <c:choose>
            <%-- CAS 1 : LE JOUEUR EST CONNECTÉ --%>
            <c:when test="${not empty sessionScope.sessionJoueur}">
                <div style="text-align: left;">
                    <h2 style="margin: 0;">🕹️ Prêt, <span style="color:#00FF00;">${sessionScope.sessionJoueur.pseudo}</span> ?</h2>
                </div>
                    <a href="profil" class="btn btn-profil">👤 Mon Profil</a>
                    <a href="cosmetique" class="btn btn-boutique">🛍️ Boutique</a>
                    <a href="deconnexion" class="btn btn-logout">Déconnexion</a>
                <div>
                    
                </div>
            </c:when>
            
            <%-- CAS 2 : LE JOUEUR EST UN INVITÉ (Non connecté) --%>
            <c:otherwise>
                <div style="text-align: left;">
                    <h2 style="margin: 0; color: #aaa;">🕹️ Bienvenue, Invité !</h2>
                    <p style="margin: 5px 0 0 0; color: #888; font-size: 14px;">Connecte-toi pour sauvegarder tes scores et débloquer des objets.</p>
                </div>
                <div>
                    <a href="connexion" class="btn btn-login">INSERT COIN (Connexion)</a>
                    <a href="deconnexion" class="btn btn-logout">Déconnexion</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <h1>🏆 LEADERBOARD MONDIAL 🏆</h1>
    
    <c:if test="${not empty erreur}">
        <p style="color: #FF5555; font-weight: bold;">❌ ${erreur}</p>
    </c:if>

    <table>
        <tr>
            <th>Rang</th>
            <th>Joueur</th>
            <th>Record Pac-Man</th>
            <th>Record Fantôme</th>
        </tr>
        
        <c:forEach items="${scores}" var="joueur" varStatus="status">
            <tr>
                <td class="
                    <c:choose>
                        <c:when test="${status.count == 1}">rank-1</c:when>
                        <c:when test="${status.count == 2}">rank-2</c:when>
                        <c:when test="${status.count == 3}">rank-3</c:when>
                    </c:choose>
                ">
                    # ${status.count}
                </td>
                
                <td style="font-weight: bold; color: #00FF00;">${joueur.pseudo}</td>
                <td>${joueur.scorePacman}</td>
                <td>${joueur.scoreFantome}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>