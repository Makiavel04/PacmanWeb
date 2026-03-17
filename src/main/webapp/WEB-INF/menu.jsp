<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Pac-Man Dashboard</title></head>
<body>
    <h1>🕹️ Bienvenue, ${sessionScope.sessionJoueur.pseudo} !</h1>
    <h2>💰 Ton portefeuille : ${sessionScope.sessionJoueur.pacgommes} Pac-Gommes</h2>

    <hr>

    <h2>🏆 Leaderboard Mondial 🏆</h2>
    <table border="1">
        <tr><th>Rang</th><th>Pseudo</th><th>Score</th></tr>
        <c:forEach items="${scores}" var="joueur" varStatus="status">
            <tr>
                <td># ${status.count}</td>
                <td>${joueur.pseudo}</td>
                <td>${joueur.meilleurScore}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>