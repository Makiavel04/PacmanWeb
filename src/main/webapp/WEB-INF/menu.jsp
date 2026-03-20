<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Dashboard</title>
    <style>
        body { background-color: #111; color: #fff; font-family: 'Courier New', Courier, monospace; text-align: center; margin: 30px; }
        .header { display: flex; justify-content: space-between; align-items: center; background: #222; padding: 20px; border: 2px solid #FFD700; border-radius: 8px; margin-bottom: 30px;}
        .stats { text-align: left; }
        .stats h2 { margin: 5px 0; color: #FFD700; }
        .stats p { margin: 5px 0; font-size: 18px; }
        .btn-logout { background: #FF5555; color: #fff; padding: 10px 15px; text-decoration: none; border-radius: 5px; font-weight: bold; border: 2px solid #CC0000;}
        .btn-logout:hover { background: #CC0000; }
        
        table { width: 80%; margin: 0 auto; border-collapse: collapse; background: #222; border: 2px solid #FFD700; box-shadow: 0 0 10px #FFD700;}
        th { background: #FFD700; color: #000; padding: 15px; font-size: 18px; text-transform: uppercase;}
        td { padding: 12px; border-bottom: 1px solid #444; font-size: 16px;}
        tr:hover { background: #333; }
        .rank-1 { color: #FFD700; font-weight: bold; font-size: 20px;}
        .rank-2 { color: #C0C0C0; font-weight: bold; }
        .rank-3 { color: #CD7F32; font-weight: bold; }
    </style>
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
            <a href="cosmetique" class="btn-logout" style="background: #00FFFF; color: #000; border-color: #00CCCC; margin-right: 10px;">Boutique & Inventaire</a>
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
                
                <td style="font-weight: bold; color: #00FF00;">${joueur.pseudo}</td>
                <td>${joueur.scorePacman}</td>
                <td>${joueur.scoreFantome}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>