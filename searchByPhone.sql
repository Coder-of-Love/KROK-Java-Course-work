SELECT *
FROM Employees e
WHERE e.id = (SELECT e.id
            FROM Employees e, AdditionalInfo a
            WHERE e.afID = a.id
                    AND
                    a.phone = "555")