angular.module("app", [])

    .controller('home', function($scope, dataService) {

        $(document).ready(function(){
            console.log("Give me jobs automatically!");
            dataService.inprogressJobs(function (response) {
                console.log(response.data);
                $scope.jobsInProgress = response.data;
            });
        })
    })

    .service('dataService', function($http){
        this.inprogressJobs = function(callback){
            $http.get("/job/list/inprogress")
                .then(callback)
        };
    });