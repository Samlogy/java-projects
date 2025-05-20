import React, {
    createContext,
    useEffect,
    useState,
    useRef,
  } from 'react'
  import Keycloak from 'keycloak-js'
  
  interface KeycloakContextProps {
    keycloak: Keycloak | null
    authenticated: boolean
  }
  
  const KeycloakContext = createContext<KeycloakContextProps | undefined>(
    undefined,
  )
  
  interface KeycloakProviderProps {
    children: React.ReactNode
  }
  
  const KeycloakProvider: React.FC<KeycloakProviderProps> = ({ children }) => {
    const isRun = useRef<boolean>(false)
    const [keycloak, setKeycloak] = useState<Keycloak | null>(null)
    const [authenticated, setAuthenticated] = useState<boolean>(false)
  
    useEffect(() => {
      if (isRun.current) return
  
      isRun.current = true
  
      const initKeycloak = async () => {

        const keycloakConfig = {
            realm: 'my-realm',
            url: 'http://localhost:8181',
            clientId: 'my-client-realm',
          }
        // const keycloackConfig = {
        //   url: import.meta.env.VITE_KEYCLOAK_URL as string,
        //   realm: import.meta.env.VITE_KEYCLOAK_REALM as string,
        //   clientId: import.meta.env.VITE_KEYCLOAK_CLIENT as string,
        // }
        const kc: Keycloak = new Keycloak(keycloakConfig)

        kc.init({
          onLoad: 'login-required', // Supported values: 'check-sso' , 'login-required'
          checkLoginIframe: true,
          pkceMethod: 'S256'
        }).then((auth) => {
          if (!auth) {
            window.location.reload();
          } else {
            /* Remove below logs if you are using this on production */
            console.info("Authenticated");
            console.log('auth', auth)
            console.log('Keycloak', kc)
            console.log('Access Token', kc.token)
        
            /* http client will use this header in every request it sends */
            // httpClient.defaults.headers.common['Authorization'] = `Bearer ${kc.token}`;
        
            kc.onTokenExpired = () => {
              console.log('token expired')
            }
          }
        }, () => {
          /* Notify the user if necessary */
          console.error("Authentication Failed");
        });
  
        // keycloakInstance
        //   .init({
        //     onLoad: 'check-sso',
        //   })
        //   .then((authenticated: boolean) => {
        //     setAuthenticated(authenticated)
        //   })
        //   .catch((error) => {
        //     console.error('Keycloak initialization failed:', error)
        //     setAuthenticated(false)
        //   })
        //   .finally(() => {
        //     setKeycloak(keycloakInstance)
        //     console.log('keycloak', keycloakInstance)
        //   })
      }
  
      initKeycloak()
    }, [])
  
    return (
      <KeycloakContext.Provider value={{ keycloak, authenticated }}>
        {children}
      </KeycloakContext.Provider>
    )
  }
  
  export { KeycloakProvider, KeycloakContext }