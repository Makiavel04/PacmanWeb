<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pac-Man : Boutique & Inventaire</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css">
</head>
<body>

    <div class="header">
        <div>
            <a href="menu" class="btn-retour">⬅️ RETOUR AU MENU</a>
        </div>
        <div>
            <h3>🛍️ BOUTIQUE PAC-MAN 🛍️</h3>
            <p>Joueur : <span>${sessionScope.sessionJoueur.pseudo}</span></p>
        </div>
    </div>

    <c:if test="${not empty erreur}">
        <p class="erreur">❌ ${erreur}</p>
    </c:if>

    <h2>🎒 MON INVENTAIRE</h2>
    <div class="grid-container">
        <c:choose>
            <c:when test="${empty mycos}">
                <p class="empty-msg">Ton inventaire est vide. Va faire un tour dans la boutique !</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${mycos}" var="item">
                    <div class="card">
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
    </div>

</body>
</html>