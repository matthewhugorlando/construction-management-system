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

