<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Mon Profil</title>
    <style>
        body { background-color: #111; color: #fff; font-family: 'Courier New', Courier, monospace; text-align: center; margin: 30px; }
        .box-profil { background: #222; border: 2px solid #FFD700; border-radius: 10px; padding: 20px; width: 60%; margin: 0 auto 30px auto; text-align: left; box-shadow: 0 0 15px #FFD700;}
        h1 { color: #FFD700; }
        .btn-retour { background: #555; color: #fff; padding: 10px 15px; text-decoration: none; border-radius: 5px; font-weight: bold; border: 2px solid #888; display: inline-block; margin-bottom: 20px;}
        .btn-retour:hover { background: #777; }
        
        table { width: 80%; margin: 0 auto; border-collapse: collapse; background: #222; border: 1px solid #555;}
        th { background: #444; color: #fff; padding: 10px;}
        td { padding: 10px; border-bottom: 1px solid #333;}
        .victoire { color: #00FF00; font-weight: bold; }
        .defaite { color: #FF5555; font-weight: bold; }
    </style>
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