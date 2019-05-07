<!DOCTYPE html>
<html>
  <head>  
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="A simlpe Secret Santa with MEAN stack">
        <meta name="keywords" content="serect-santa, MEAN stack, christmas">
        <title>Secret Santa</title>
        <link rel="icon" href="static/images/hat.png" type="image" sizes="16x16">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link href="static/css/app.css" rel="stylesheet">
  </head>
    <body data-ng-app="secretSantaApp">
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#/"><img src="static/images/santa.png" class="img-responsive img-circle" alt="Responsive image"></a>
                </div>
        </nav>
        <div class="container-fluid centers" data-ng-controller="secretSantaCtrl">
            <div class="row">
                <div class="col-xs-12 col-md-4">
                   <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3 class="panel-title">Admin Panel</h3>
                        </div>
                        <div class="panel-body ng-cloak">
                            <table class="table">
                                <tbody>
                                    <tr>
                                        <td>Number of Our Santas</td>
                                        <td>{{ santas }}</td>
                                    </tr>
                                    <tr>
                                        <td>Last Santa</td>
                                        <td>{{ lastSanta }}</td>
                                    </tr>
                                    <tr>
                                        <td>Registration Active</td>
                                        <td>{{ !terminate }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <form novalidate name="adminForm">
                                <div class="form-group">
                                    <header data-ng-show="passAlert" class="alert alert-danger" role="alert">
                                        You entered wrong user/pass info!
                                    </header>
                                    <label for="email">
                                        Email <span class="alert alert-danger" data-ng-show="adminForm.email.$touched && adminForm.email.$invalid">
                                        <span data-ng-show="adminForm.email.$error.required">is required. </span>
                                        <span data-ng-show="adminForm.email.$error.email">Invalid email address.</span>
                                    </span>
                                    </label>
                                    <input type="email" id="email" placeholder="admin@smithfamily.com" class="form-control" data-ng-model="admin.email" name="email" required>
                                </div>
                                <div class="form-group">
                                    <label for="pass">
                                        Password <span class="alert alert-danger" data-ng-show="adminForm.pass.$touched && adminForm.pass.$invalid">
                                        <span data-ng-show="adminForm.pass.$error.required">
                                        is required
                                        </span>
                                    </span>
                                    </label>
                                    <input type="password" id="pass" placeholder="Password" class="form-control" data-ng-model="admin.pass" name="pass" required>
                                </div>
                                <button class="btn btn-primary" data-ng-click="makeMatch()" data-ng-disabled="adminForm.pass.$touched && adminForm.pass.$invalid || adminForm.email.$touched && adminForm.email.$invalid">TERMINATE & MAKE MATCHES</button>
                                <br/>
                                <button class="btn btn-danger pull-right" data-ng-click="deleteRenew()"  data-ng-disabled="adminForm.pass.$touched && adminForm.pass.$invalid || adminForm.email.$touched && adminForm.email.$invalid">DELETE & RENEW</button>
                            </form>
                        </div>
                        <div class="panel-footer">
                            <footer>* Only Admin can terminate & make matches.</footer>
                            <footer>* Only Admin can delecte and renew santa database.</footer>
                            <footer>* Only Admin has email/pass.</footer>
                            <footer>* Correct email/pass are required for above actions.</footer>
                        </div>
                    </div> 
                </div>
                <div class="col-xs-12 col-md-4">
                   <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3 class="panel-title">Santa Sign Up Center</h3>
                        </div>
                        <div class="panel-body ng-cloak">
                            <p>
                                To sign up for the gift exchange enter your name, please.
                            </p>
                            <form novalidate name="myRegisterForm">
                                <div class="form-group">
                                    <header data-ng-show="duplicatedAlert" class="alert alert-danger" role="alert">
                                        This Santa info was entered before.
                                    </header>
                                    <label for="name">
                                        Name <span class="alert alert-danger" data-ng-show="myRegisterForm.name.$dirty && myRegisterForm.name.$invalid">
                                         <span data-ng-show="myRegisterForm.name.$error.required">
                                         is required.
                                        </span>
                                    </span>
                                    </label>
                                    <input type="text" id="name" placeholder="John" class="form-control" data-ng-model="santa.name" name="name" required data-ng-disabled="terminate">
                                </div>
                                <div class="form-group">
                                    <label for="spouse">
                                        Spouse Name If Applicable
                                    </label>
                                    <input type="text" id="spouse" placeholder="Sophia" class="form-control" data-ng-model="santa.spouse" data-ng-disabled="terminate">
                                </div>
                                <button class="btn btn-primary" data-ng-disabled="myRegisterForm.name.$dirty && myRegisterForm.name.$invalid || terminate" data-ng-click="addSanta()">SINGUP</button>
                            </form>
                        </div>
                        <div class="panel-footer">
                            <footer>* We assume that all the participants have a unique name.</footer>
                        </div>
                    </div> 
                </div>
                <div class="col-xs-12 col-md-4">
                   <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3 class="panel-title">Santa Shop Center</h3>
                        </div>
                        <div class="panel-body ng-cloak">
                            <h4>Get your match here!</h4>
                            <p>Matches will be ready ones all entries are collected.</p>
                            <form novalidate name="myMatchForm">
                                <div class="form-group">
                                    <label for="name2">
                                        Name <span class="alert alert-danger" data-ng-show="myMatchForm.name2.$touched && myMatchForm.name2.$invalid">
                                         <span data-ng-show="myMatchForm.name2.$error.required">
                                         is required.
                                        </span>
                                    </span>
                                    </label>
                                    <input type="text" id="name2" placeholder="John" class="form-control" data-ng-model="gifter.name" name="name2" required>
                                </div>
                                <button class="btn btn-primary" data-ng-click="getMatch()" data-ng-disabled="myMatchForm.name2.$touched && myMatchForm.name2.$invalid">GET MY MATCH</button> <span data-ng-show="matchalert" class="alert alert-danger" role="alert">
                                    Your input was wrong!
                                </span>
                            </form>
                        </div>
                        <div class="panel-footer ng-cloak">
                            <footer data-ng-show="true">
                                Your match is: {{ match }}
                            </footer>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
        <nav class="navbar navbar-inverse navbar-fixed-bottom" role="navigation">
                <div class="footer-header">
                    MADE WITH LOVE IN MONTREAL 2017-19
                </div>
        </nav>
      
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="static/js/app.js"></script>
      <script src="static/js/controller/secret-santa.js"></script>
      <script src="static/js/service/santafactory.js"></script>
  </body>
</html>