import Keycloak from "keycloak-js";

const keycloakInstance = new Keycloak({
  url: "http://localhost:8181/",
  realm: "my-realm",
  clientId: "my-client-id"
});

export default keycloakInstance;
