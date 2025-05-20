


**TODOS:**
get data from => api dbnomics ?
alerting => email, slack ?

**Tests Spring batch:**

```bash
# delete all records
curl -X DELETE http://localhost:8080/api/jobs/deleteAll

# import data from .csv => postgresql
curl -X GET http://localhost:8080/api/jobs/importData
```

**Features:**

✔ Handles errors gracefully. 
✔ Retries temporary failures 
✔ Logs issues for debugging 
✔ code structure 
✔ Schedule job execution


