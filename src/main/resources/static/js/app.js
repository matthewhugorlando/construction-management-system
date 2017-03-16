var clientId = 'clientId';
var jobId = 'jobId';
var itemId = 'itemId';

angular.module("app", [])

    .controller('home', function($scope, dataService) {

        $(document).ready(function(){
            console.log("Give me jobs automatically!");
            dataService.inProgressJobs(function (response) {
                console.log(response.data);
                $scope.jobsInProgress = response.data;
            });
        })
    })

    // ==================
    // Clients Controllers
    // ==================

    .controller('newclient', function($scope, $http) {
        $(document).ready(function(){
            console.log("New Client Controller checking in!");
        });

        $scope.submitClient = function(){
            console.log("Client Submitted!");
            var dataObj = {
                test : "test"
            };

            var newClient = {
                    name: $scope.name,
                    contactFirstName: $scope.fn,
                    contactLastName: $scope.ln,
                    contactPhoneNumber: $scope.phone,
                    contactEmail: $scope.cEmail,
                    address : {
                        street : $scope.street,
                        city : $scope.city,
                        state : $scope.state,
                        zipCode : $scope.zip
                    }
            };

            var res = $http.post('/client/new', newClient);
            res.success(function(data, status, headers, config) {
                $scope.clientNew = data;
            });
            res.error(function(data, status, headers, config) {
            });

            $scope.name = '';
            $scope.fn = '';
            $scope.ln = '';
            $scope.phone = '';
            $scope.cEmail = '';
            $scope.street = '';
            $scope.city = '';
            $scope.state = '';
            $scope.zip = '';


        }

    })

    .controller('listClients', function($scope, $http) {

        $(document).ready(function(){
            var res = $http.get("/client/list");
            res.success(function(data, status, headers, config) {
                $scope.clientList = data;
            });
        });

        $scope.selectClient = function(id){
            window.localStorage.setItem(clientId, id);
        };
    })

    .controller('indvClient', function($scope, $http){
        $(document).ready(function(){
            console.log("Client page!" + localStorage.getItem(clientId));
            var url = "/client/select?id=" + window.localStorage.getItem(clientId);
            var res = $http.get(url);
            res.success(function(data, status, headers, config) {
               $scope.cl = data;
            });
            window.localStorage.setItem('clientId', "");
        })
    })

    // ==================
    // Jobs Controllers
    // ==================

    .controller('listJobs', function($scope, $http) {
        $(document).ready(function(){
            var res = $http.get("/job/list");
            res.success(function(data, status, headers, config) {
                $scope.jobList = data;
            });
        });

        $scope.selectJob = function(id){
            window.localStorage.setItem(jobId, id);
        };
    })

    .controller('indvJob', function($scope, $http){
        $(document).ready(function(){
            console.log("Job page!" + localStorage.getItem(jobId));
            var url = "/job/select?id=" + window.localStorage.getItem(jobId);
            var res = $http.get(url);
            res.success(function(data, status, headers, config) {
                $scope.jo = data;
            });
            window.localStorage.setItem(jobId, "");
        })
    })

    // ==================
    // Items Controllers
    // ==================

    .controller('newItem', function($scope, $http) {
        $(document).ready(function(){
            var res1 = $http.get("/job/list");
            res1.success(function(data, status, headers, config) {
                $scope.cjs = data;
            });
            var res2 = $http.get("/inventory/itemtype/list");
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });
        });

        $scope.submitItem = function(){
            console.log("Item Submitted!");


            var newItem = {
                quantity : $scope.qty,
                status : $scope.status,
                typeId : $scope.type,
                locId : $scope.jLoc
            };

            var res = $http.post('/inventory/item/new', newItem);
            res.success(function(data, status, headers, config) {
                $scope.clientNew = data;
            });
            res.error(function(data, status, headers, config) {
            });

            $scope.qty = '';
            $scope.status = '';
            $scope.type = '';
            $scope.jLoc = '';
        }

    })

    .controller('listItems', function($scope, $http) {
        $(document).ready(function(){

            var res1 = $http.get("/inventory/list");
            res1.success(function(data, status, headers, config) {
                $scope.itemList = data;
            });
            var res2 = $http.get("/job/list");
            res2.success(function(data, status, headers, config) {
                $scope.jobList = data;
                console.log(data);
                var jNames = [];

                for(i=0;i<data.length;i++){
                    console.log(data[i]);
                    jNames[data[i].id] = data[i].name;
                }

                $scope.jn = jNames;
            });

        });

        $scope.selectItem = function(id){
            window.localStorage.setItem(itemId, id);
        };
    })

    .controller('indvItem', function($scope, $http){
        $(document).ready(function(){
            console.log("Item page!" + localStorage.getItem(jobId));
            var url = "/inventory/select?id=" + window.localStorage.getItem(itemId);
            var res = $http.get(url);
            res.success(function(data, status, headers, config) {
                $scope.it = data;
            });
            window.localStorage.setItem(itemId, "");
        })
    })

    .controller('listTypes', function($scope, $http) {
        $(document).ready(function(){
            var res = $http.get("/inventory/itemtype/list");
            res.success(function(data, status, headers, config) {
                $scope.itemTypes = data;
            });
        });

        $scope.selectType = function(id){
            window.localStorage.setItem(typeId, id);
        };
    })

    .service('dataService', function($http){
        this.inProgressJobs = function(callback){
            $http.get("/job/list/inprogress")
                .then(callback)
        };

        this.listOfClients = function(){
            $http.get("/client/list")
        };

        this.clientSubmit = function(){
            $http.post("/client/new", data)
                .then(successCallback, errorCallback);
        }
    });

