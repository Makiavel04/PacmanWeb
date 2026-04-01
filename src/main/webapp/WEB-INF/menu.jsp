<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css">
</head>
<body>

    <div class="header">
        <div class="stats">
            <h2>🕹️ Bienvenue, ${sessionScope.sessionJoueur.pseudo} !</h2>
            <p>🟡 Record Pac-Man : <b>${sessionScope.sessionJoueur.scorePacman}</b> pts</p>
            <p>👻 Record Fantôme : <b>${sessionScope.sessionJoueur.scoreFantome}</b> pts</p>
        </div>
        <div>
            <a href="deconnexion" class="btn-logout">Déconnexion</a>
            <a href="cosmetique" class="btn-logout">Boutique & Inventaire</a>
        </div>
    </div>

    <h1>🏆 LEADERBOARD MONDIAL 🏆</h1>
    
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
                
                <td>${joueur.pseudo}</td>
                <td>${joueur.scorePacman}</td>
                <td>${joueur.scoreFantome}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>