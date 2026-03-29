<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Dashboard</title>
    <style>
        body { background-color: #111; color: #fff; font-family: 'Courier New', Courier, monospace; text-align: center; margin: 30px; }
        
        /* En-tête / Barre de navigation */
        .header { display: flex; justify-content: space-between; align-items: center; background: #222; padding: 20px; border: 2px solid #FFD700; border-radius: 8px; margin-bottom: 30px;}
        
        /* Boutons du menu */
        .btn { padding: 10px 15px; text-decoration: none; border-radius: 5px; font-weight: bold; margin-left: 10px; display: inline-block; border: 2px solid; text-transform: uppercase;}
        .btn-login { background: #00FF00; color: #000; border-color: #00CC00; font-size: 16px; padding: 15px 25px; animation: clignote 1s infinite;}
        .btn-profil { background: #FFD700; color: #000; border-color: #CCAA00; }
        .btn-boutique { background: #00FFFF; color: #000; border-color: #00CCCC; }
        .btn-logout { background: #FF5555; color: #fff; border-color: #CC0000; }
        
        .btn:hover { filter: brightness(1.2); transform: scale(1.05); }

        /* Effet borne d'arcade pour le bouton connexion */
        @keyframes clignote {
            0% { opacity: 1; }
            50% { opacity: 0.7; box-shadow: 0 0 15px #00FF00; }
            100% { opacity: 1; }
        }

        /* Tableau des scores */
        h1 { color: #FFD700; text-shadow: 2px 2px #000; margin-top: 40px;}
        table { width: 80%; margin: 0 auto; border-collapse: collapse; background: #222; border: 2px solid #FFD700; box-shadow: 0 0 10px #FFD700;}
        th { background: #FFD700; color: #000; padding: 15px; font-size: 18px; text-transform: uppercase;}
        td { padding: 12px; border-bottom: 1px solid #444; font-size: 16px;}
        tr:hover { background: #333; }
        
        /* Couleurs du podium */
        .rank-1 { color: #FFD700; font-weight: bold; font-size: 20px;}
        .rank-2 { color: #C0C0C0; font-weight: bold; }
        .rank-3 { color: #CD7F32; font-weight: bold; }
    </style>
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