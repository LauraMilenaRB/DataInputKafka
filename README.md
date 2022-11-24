# 2022-PFSD-Session-06

Debido a la inclusión de la librería de protobuf, el build a realizar del proyecto es más complejo, por ende se
recomienda usar el shell de sbt y no el compilador que IntelliJ ofrece por defecto.

Para hacer esto por favor siga los siguientes pasos:

1. Haga click en _File_ -> _Settings_

![fileSettings.png](img/fileSettings.png)

2. Luego haga click en _Build, Execution, Deployments_ -> _Build Tools_ -> _sbt_
    1. En la sección **sbt shell**, seleccione el check de la opción _builds_ y click en _**Apply**_

![buildsSbt.png](img/buildsSbt.png)

2. Una vez hecho ese cambio, cargar el archivo build.sbt
3. Finalmente compile el proyecto de nuevo con _Build_ -> _Build project_

![buildProject.png](img/buildProject.png)

### Ejercicios a desarrollar: ###

1. Agregar puertos y adaptadores 
- ForUpdatingRepository
- ForTakingBackup
2. Agregar los comandos para guardar transacciones
- BackupTransactionCommand
- SaveIncomeTransactionCommand
- SaveExpenseTransactionCommand
3. Resolver el Two Phase commit existente
4. Defina un puerto, un adaptador y un query enfocado en la parte **Query** de CQRS.
5. Implemente la función _getTransactionsByCategory_ en **TransactionDAO**
