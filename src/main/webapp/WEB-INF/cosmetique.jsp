<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Boutique & Inventaire</title>
    <style>
        body { background-color: #111; color: #fff; font-family: 'Courier New', Courier, monospace; text-align: center; margin: 30px; }
        
        /* En-tête */
        .header { display: flex; justify-content: space-between; align-items: center; background: #222; padding: 20px; border: 2px solid #00FFFF; border-radius: 8px; margin-bottom: 30px;}
        .btn-retour { background: #555; color: #fff; padding: 10px 15px; text-decoration: none; border-radius: 5px; font-weight: bold; border: 2px solid #888;}
        .btn-retour:hover { background: #777; }
        
        h1 { color: #00FFFF; text-shadow: 2px 2px #000; }
        h2 { color: #FFD700; border-bottom: 2px dashed #444; padding-bottom: 10px; display: inline-block;}
        .erreur { color: #FF5555; font-weight: bold; font-size: 18px; }

        /* Grille des objets */
        .grid-container { display: flex; flex-wrap: wrap; justify-content: center; gap: 20px; margin-bottom: 50px; }
        
        /* Carte d'un objet cosmétique */
        .card { background: #222; border: 2px solid #444; border-radius: 10px; padding: 20px; width: 200px; box-shadow: 0 0 10px #000; transition: transform 0.2s;}
        .card:hover { transform: scale(1.05); border-color: #00FFFF; }
        
        /* Le cercle de couleur généré dynamiquement ! */
        .color-swatch { width: 60px; height: 60px; border-radius: 50%; border: 3px solid #fff; margin: 0 auto 15px auto; box-shadow: 0 0 10px rgba(255,255,255,0.2);}
        
        .nom-item { font-size: 18px; font-weight: bold; margin-bottom: 15px; color: #fff;}
        
        /* Boutons d'action */
        .btn-action { width: 100%; padding: 10px; font-weight: bold; border: none; border-radius: 5px; cursor: pointer; font-family: 'Courier New'; text-transform: uppercase;}
        .btn-equiper { background-color: #00FF00; color: #000; }
        .btn-equiper:hover { background-color: #00CC00; }
        .btn-acheter { background-color: #FFD700; color: #000; }
        .btn-acheter:hover { background-color: #FFC107; }
        
        .empty-msg { color: #888; font-style: italic; }
    </style>
</head>
<body>

    <div class="header">
        <div>
            <a href="menu" class="btn-retour">⬅️ RETOUR AU MENU</a>
        </div>
        <div>
            <h3>🛍️ BOUTIQUE PAC-MAN 🛍️</h3>
            <p>Joueur : <span style="color:#00FF00;">${sessionScope.sessionJoueur.pseudo}</span></p>
        </div>
    </div>

    <c:if test="${not empty erreur}">
        <p class="erreur">❌ ${erreur}</p>
    </c:if>

    <h2>🎒 MON INVENTAIRE</h2>
    <div class="grid-container">
        <c:choose>
            <c:when test="${empty mycos}">
                <p class="empty-msg">Ton inventaire est vide. looser !</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${mycos}" var="item">
                    <div class="card" style="border-color: #00FF00;">
                        <div class="color-swatch" style="background-color: ${item.couleur};"></div>
                        <div class="nom-item">${item.nomCosmetique}</div>
                        
                        <form action="cosmetique" method="post">
                            <input type="hidden" name="action" value="equiper">
                            <input type="hidden" name="idCosmetique" value="${item.id}">
                            <button type="submit" class="btn-action btn-equiper">S'ÉQUIPER</button>
                        </form>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <h2>🛒 TOUS LES COSMÉTIQUES</h2>
    <div class="grid-container">
        <c:choose>
            <c:when test="${empty cos}">
                <p class="empty-msg">La boutique est vide  tkt le iencli va bientot etre viser !</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${cos}" var="boutiqueItem">
                    <div class="card">
                        <div class="color-swatch" style="background-color: ${boutiqueItem.couleur};"></div>
                        <div class="nom-item">${boutiqueItem.nomCosmetique}</div>
                        
                        <form action="cosmetique" method="post">
                            <input type="hidden" name="action" value="acheter">
                            <input type="hidden" name="idCosmetique" value="${boutiqueItem.id}">
                            <button type="submit" class="btn-action btn-acheter">ACHETER</button>
                        </form>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>