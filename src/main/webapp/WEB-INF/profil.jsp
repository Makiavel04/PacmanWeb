<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Mon Profil</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/main_theme.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/profil.css' />">
</head>
<body>

    <div style="text-align: left;">
        <a href="menu" class="btn-retour">⬅️ RETOUR AU MENU</a>
    </div>

    <h1>👤 CARTE DE JOUEUR</h1>

    <div class="box-profil">
        <h2 style="color: #00FF00; margin-top: 0;">${sessionScope.sessionJoueur.pseudo}</h2>
        <p>🟡 Meilleur Score Pac-Man : <b>${sessionScope.sessionJoueur.scorePacman}</b></p>
        <p>👻 Meilleur Score Fantôme : <b>${sessionScope.sessionJoueur.scoreFantome}</b></p>
    </div>

    <h2>📜 HISTORIQUE DES PARTIES</h2>
    
    <c:choose>
        <c:when test="${empty historique}">
            <p style="color: #aaa; font-style: italic;">Tu n'as pas encore joué de partie. Lance le jeu !</p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>N° Partie</th>
                    <th>Score Pac-Man</th>
                    <th>Score Fantôme</th>
                    <th>Vainqueur</th>
                </tr>
                <c:forEach items="${historique}" var="partie">
                    <tr>
                        <td># ${partie.id}</td>
                        <td>${partie.scorePacmans}</td>
                        <td>${partie.scoreFantomes}</td>
                        <td class="${partie.vainqueur == 'P' ? 'victoire' : 'defaite'}">
                            ${partie.vainqueur == 'P' ? '🟡 PAC-MAN' : '👻 FANTÔMES'}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

</body>
</html>