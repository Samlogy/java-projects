
## Infra SCAN, OWAP Top 10 SCAN
```bash
# Scan configs

Kubernetes, terraform, ansible, ...

# Analyse de sécurité pour Terraform (Scan)
pip3 install checkov

# run scan => terraform code
checkov --file main.tf > output.json



# OWASP ZAP
# run DAST Scan in Test Env
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://localhost:3001
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py -t http://localhost:3001
```