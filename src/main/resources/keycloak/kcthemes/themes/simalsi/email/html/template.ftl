<#macro emailLayout>
<html>
<body>
    <div style="background: url('${url.resourcesUrl}/img/keycloak-bg.jpg'); background-size: cover; display: flex; justify-content: center; align-items: center; min-height: 100vh; font-family: ui-sans-serif, system-ui, sans-serif">
        <div style="background-color: #124dac; border-radius: 8px; padding: 4px;">
            <div style="color: white; text-align: center; font-family: ui-sans-serif, system-ui, sans-serif; font-weight: 500; font-size: 22px; margin-bottom: 12px;">LABORATORIO DR. MARIO ANTONIO LÓPEZ SOMARRIBA</div>
            <div style="margin: 0 auto; color: black; background: white; max-width: 580px; border: 1px rgb(225, 225, 225) solid; border-radius: 8px; overflow: hidden; ">
                <#nested>
            </div>
        </div>
    </div>
</body>
</html>
</#macro>
