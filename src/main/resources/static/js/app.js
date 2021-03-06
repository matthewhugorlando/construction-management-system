var clientId = 'clientId';
var jobId = 'jobId';
var itemId = 'itemId';

var authconfig = {
    headers: {
        'x-authorization-key': window.localStorage.getItem("token")
    }
};

angular.module("app", [])


    // ==================
    // Login Controller
    // ==================

    .controller('login', function($scope, $http) {

        $(document).ready(function(){
            console.log("Welcome to login!");
        });

        $scope.attemptLogin = function (){
            $scope.jobTracker += 1;
            var url2 = "/rest/user/login?u=" + $scope.username + "&p=" + $scope.password;
            var res2 = $http.get(url2, authconfig);
            res2.success(function(data, status, headers, config) {
                window.localStorage.setItem("token", data.token);
                window.location.replace("/index.html");
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

        };

    })

    // ==================
    // Index Controller
    // ==================

    .controller('home', function($scope, $http) {

        $(document).ready(function(){
            $scope.tokenCheck = window.localStorage.getItem("token");
            $scope.jobTracker = 0;
            $scope.tasksOfJIP = [];
            console.log("Give me jobs automatically!");
            var res1 = $http.get("/rest/job/list/inprogress", authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.jobsInProgress = data;
                var url2 = "/rest/inventory/status/loc/find?locId=" + $scope.jobsInProgress[$scope.jobTracker].id + "&status=Pending Delivery";
                var res2 = $http.get(url2, authconfig);
                res2.success(function(data, status, headers, config) {
                    $scope.invOfJIP = data;
                    for(i=0;i<$scope.invOfJIP.length;i++){
                        $scope.invOfJIP[i].showCost = $scope.invOfJIP[i].totalCost.toFixed(2);

                    }
                });
                for(i=0;i<$scope.jobsInProgress[$scope.jobTracker].tasks.length;i++){
                    if($scope.jobsInProgress[$scope.jobTracker].tasks[i].completed === false){
                        $scope.tasksOfJIP.push($scope.jobsInProgress[$scope.jobTracker].tasks[i]);
                    }
                }
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });


        $scope.nextJob = function (){
            $scope.jobTracker += 1;
            var url2 = "/rest/inventory/status/loc/find?locId=" + $scope.jobsInProgress[$scope.jobTracker].id + "&status=Pending Delivery";
            var res2 = $http.get(url2, authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.invOfJIP = data;
                for(i=0;i<$scope.invOfJIP.length;i++){
                    $scope.invOfJIP[i].showCost = $scope.invOfJIP[i].totalCost.toFixed(2);
                }
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            $scope.tasksOfJIP = [];
            for(i=0;i<$scope.jobsInProgress[$scope.jobTracker].tasks.length;i++){
                if($scope.jobsInProgress[$scope.jobTracker].tasks[i].completed === false){
                    $scope.tasksOfJIP.push($scope.jobsInProgress[$scope.jobTracker].tasks[i]);
                }
            }

        };

        $scope.prevJob = function (){
            $scope.jobTracker -= 1;
            var url2 = "/rest/inventory/status/loc/find?locId=" + $scope.jobsInProgress[$scope.jobTracker].id + "&status=Pending Delivery";
            var res2 = $http.get(url2, authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.invOfJIP = data;
                for(i=0;i<$scope.invOfJIP.length;i++){
                    $scope.invOfJIP[i].showCost = $scope.invOfJIP[i].totalCost.toFixed(2);

                }
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            $scope.tasksOfJIP = [];
            for(i=0;i<$scope.jobsInProgress[$scope.jobTracker].tasks.length;i++){
                if($scope.jobsInProgress[$scope.jobTracker].tasks[i].completed === false){
                    $scope.tasksOfJIP.push($scope.jobsInProgress[$scope.jobTracker].tasks[i]);
                }
            }
        };

        $scope.selectJob = function(id){
            window.localStorage.setItem(jobId, id);
        };

        $scope.toJobsList = function(){
            console.log("ToJobsList function called");
            window.location.href = '/rest/jobs/list.html';
        }

        $scope.logout = function (){
            console.log("logout called");
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    // ==================
    // Clients Controllers
    // ==================

    .controller('newClient', function($scope, $http) {
        $(document).ready(function(){
            $scope.addedClient = false;
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

            var res = $http.post('/rest/client/new', newClient, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.clientNew = data;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
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
            $scope.addedClient = true;
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    .controller('listClients', function($scope, $http, $filter) {

        $(document).ready(function(){
            var res = $http.get("/rest/client/list", authconfig);
            res.success(function(data, status, headers, config) {
                $scope.clientList = $filter('orderBy')(data, 'name')
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.selectClient = function(id){
            window.localStorage.setItem(clientId, id);
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        };

    })

    .controller('editClient', function($scope, $http) {
        $(document).ready(function(){
            console.log("Edit Client Controller checking in!");
            var res = $http.get('/rest/client/select?id=' + window.localStorage.getItem(clientId), authconfig);
            res.success(function(data, status, headers, config) {
                $scope.client = data;
                $scope.name = data.name;
                $scope.fn = data.contactFirstName;
                $scope.ln = data.contactLastName;
                $scope.phone = data.contactPhoneNumber;
                $scope.cEmail = data.contactEmail;
                $scope.street =  data.address.street;
                $scope.city = data.address.city;
                $scope.state = data.address.state;
                $scope.zip = data.address.zipCode;

                switch (data.state) {
                    case "AK":
                        $scope.state = "Alaska";
                        break;
                    case "AL":
                        $scope.state = "Alabama";
                        break;
                    case "AR":
                        $scope.state = "Arkansas";
                        break;
                    case "AZ":
                        $scope.state = "Arizona";
                        break;
                    case "CA":
                        $scope.state = "California";
                        break;
                    case "CO":
                        $scope.state = "Colorado";
                        break;
                    case "CT":
                        $scope.state = "Connecticut";
                        break;
                    case "DC":
                        $scope.state = "District of Columbia";
                        break;
                    case "DE":
                        $scope.state = "Delaware";
                        break;
                    case "FL":
                        $scope.state = "Florida";
                        break;
                    case "GA":
                        $scope.state = "Georgia";
                        break;
                    case "HI":
                        $scope.state = "Hawaii";
                        break;
                    case "IA":
                        $scope.state = "Iowa";
                        break;
                    case "ID":
                        $scope.state = "Idaho";
                        break;
                    case "IL":
                        $scope.state = "Illinois";
                        break;
                    case "IN":
                        $scope.state = "Indiana";
                        break;
                    case "KS":
                        $scope.state = "Kansas";
                        break;
                    case "KY":
                        $scope.state = "Kentucky";
                        break;
                    case "LA":
                        $scope.state = "Louisiana";
                        break;
                    case "MA":
                        $scope.state = "Massachusetts";
                        break;
                    case "MD":
                        $scope.state = "Maryland";
                        break;
                    case "ME":
                        $scope.state = "Maine";
                        break;
                    case "MI":
                        $scope.state = "Michigan";
                        break;
                    case "MN":
                        $scope.state = "Minnesota";
                        break;
                    case "MO":
                        $scope.state = "Missouri";
                        break;
                    case "MS":
                        $scope.state = "Mississippi";
                        break;
                    case "MT":
                        $scope.state = "Montana";
                        break;
                    case "NC":
                        $scope.state = "North Carolina";
                        break;
                    case "ND":
                        $scope.state = "North Dakota";
                        break;
                    case "NE":
                        $scope.state = "Nebraska";
                        break;
                    case "NH":
                        $scope.state = "New Hampshire";
                        break;
                    case "NJ":
                        $scope.state = "New Jersey";
                        break;
                    case "NM":
                        $scope.state = "New Mexico";
                        break;
                    case "NV":
                        $scope.state = "Nevada";
                        break;
                    case "NY":
                        $scope.state = "New York";
                        break;
                    case "OH":
                        $scope.state = "Ohio";
                        break;
                    case "OK":
                        $scope.state = "Oklahoma";
                        break;
                    case "OR":
                        $scope.state = "Oregon";
                        break;
                    case "PA":
                        $scope.state = "Pennsylvania";
                        break;
                    case "PR":
                        $scope.state = "Puerto Rico";
                        break;
                    case "RI":
                        $scope.state = "Rhode Island";
                        break;
                    case "SC":
                        $scope.state = "South Carolina";
                        break;
                    case "SD":
                        $scope.state = "South Dakota";
                        break;
                    case "TN":
                        $scope.state = "Tennessee";
                        break;
                    case "TX":
                        $scope.state = "Texas";
                        break;
                    case "UT":
                        $scope.state = "Utah";
                        break;
                    case "VA":
                        $scope.state = "Virginia";
                        break;
                    case "VT":
                        $scope.state = "Vermont";
                        break;
                    case "WA":
                        $scope.state = "Washington";
                        break;
                    case "WI":
                        $scope.state = "Wisconsin";
                        break;
                    case "WV":
                        $scope.state = "West Virginia";
                        break;
                    case "WY":
                        $scope.state = "Wyoming";
                        break;
                }
            });
            // res.error(function(data, status, headers, config) {
            //     window.location.replace("/login.html");
            // });
        });

        $scope.updateClient = function(){

            var clientToUpdate = {
                id: $scope.client.id,
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

            var res = $http.post('/rest/client/update', clientToUpdate, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.updatedClient = data;
            });
            // res.error(function(data, status, headers, config) {
            //     window.location.replace("/login.html");
            // });
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    .controller('indvClient', function($scope, $http){
        $(document).ready(function(){
            console.log("Client page!" + localStorage.getItem(clientId));
            $scope.tr = 0;

            var url1 = "/rest/client/select?id=" + window.localStorage.getItem(clientId);
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
               $scope.cl = data;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url2 = "/rest/client/jobs?id=" + window.localStorage.getItem(clientId) + "&s=In Progress" ;
            var res2 = $http.get(url2, authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.jobListIP = data;
                var jlip = data;
                for(i=0;i<jlip.length;i++){
                    $scope.tr = $scope.tr + jlip[i].jobPrice;
                }

            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url3 = "/rest/client/jobs?id=" + window.localStorage.getItem(clientId) + "&s=Completed" ;
            var res3 = $http.get(url3, authconfig);
            res3.success(function(data, status, headers, config) {
                $scope.jobListC = data;
                var jlc = data;
                for(i=0;i<jlc.length;i++){
                    $scope.tr = $scope.tr + jlc[i].jobPrice;
                }
            });
            res3.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url4 = "/rest/client/jobs?id=" + window.localStorage.getItem(clientId) + "&s=Pending Start" ;
            var res4 = $http.get(url4, authconfig);
            res4.success(function(data, status, headers, config) {
                $scope.jobListPS = data;
                var jlps = data;
                for(i=0;i<jlps.length;i++){
                    console.log(jlps[i].jobPrice);
                    $scope.tr = $scope.tr + jlps[i].jobPrice;
                }

            });
            res4.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

        });

        $scope.jobFromClient = function(id){
            window.localStorage.setItem(jobId, id);
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    // ==================
    // Jobs Controllers
    // ==================

    .controller('newJob', function($scope, $http) {
        $(document).ready(function(){
            $scope.items = [];
            $scope.st = false;
            var date_input=$('input[name="date"]'); //our date input has the name "date"
            var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
            var options={
                format: 'mm/dd/yyyy',
                container: container,
                todayHighlight: true,
                autoclose: true
            };
            date_input.datepicker(options);

            var res1 = $http.get("/rest/client/list", authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.clients = data;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            var res2 = $http.get("/rest/inventory/itemtype/list", authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            var res3 = $http.get("/rest/invholder/list", authconfig);
            res3.success(function(data, status, headers, config) {
                $scope.ihs = data;
            });
            res3.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

        });

        $scope.findInv = function(){
            var url1 = "/rest/invholder/find?type=" + $scope.itType;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.ihswt = data;
            });
            document.getElementById("itFrom").disabled=false;
        };

        $scope.findQty = function(){
            $scope.qs = [];
            var url1 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.itFrom;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                var cibF = data;
                $scope.cibCheck = data;
                if($scope.itFrom === "New"){
                    for(j=0;j<30;j++){
                        $scope.qs.push(j+1);
                    }
                    $scope.maxQty = 100000;
                }else {
                    for (i = 0; i < cibF.quantity; i++) {
                        $scope.qs.push(i + 1);
                    }
                    $scope.maxQty = cibF.quantity;

                }
                $scope.uom = cibF.bucketType.unitOfMeasurement;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            document.getElementById("itQty").disabled=false;
        };

        $scope.enableStatus = function (){
            document.getElementById("itStatus").disabled=false;
        };

        $scope.enableAddBtn = function (){
            document.getElementById("itAddBtn").disabled=false;
        };

        $scope.incQty = function(it){
            if($scope.items[it].qty < $scope.items[it].maxQty){
                $scope.items[it].qty += 1
            }
        };

        $scope.decQty = function(it){
            if($scope.items[it].qty > 1){
                $scope.items[it].qty -= 1
            } else if($scope.items[it].qty === 1){
                $scope.deleteItem(it);
            }
        };


        $scope.addItem = function(){
            $scope.st = true;
            var exists = false;
            var index = 0;
            var itemToAdd = {
                type : $scope.itType,
                qty : parseInt($scope.itQty),
                status : $scope.itStatus,
                from : $scope.itFrom,
                maxQty : $scope.maxQty,
                uOfM : $scope.uom
            };
            console.log("Add: " + itemToAdd.type);
            for(z=0;z<$scope.items.length;z++){
                console.log(z);

                if($scope.items[z].type === itemToAdd.type &&
                    $scope.items[z].status === itemToAdd.status &&
                    $scope.items[z].from === itemToAdd.from){
                    exists = true;
                    index = z;
                }
            }

            if(exists){
                $scope.items[index].qty = $scope.items[index].qty + itemToAdd.qty;
            }else {
                $scope.items.push(itemToAdd);
                $scope.items[$scope.items.length - 1].index = $scope.items.length - 1;
            }

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.deleteItem = function(ditem){
            console.log("Delete Item");
            var spot = ditem;
            $scope.items.splice(spot, 1);
            for(i=spot;i<$scope.items.length;i++){
                $scope.items[i].index = i;
            }
        };

        $scope.submitJob = function(){
            console.log("Job Submitted!");
            $scope.addedJob = false;


            var newJob = {
                name : $scope.name,
                clId : $scope.client,
                startDate : $scope.startDate,
                status : $scope.status,
                jobPrice : $scope.jobPrice,
                address : {
                    street : $scope.street,
                    city : $scope.city,
                    state : $scope.state,
                    zipCode : $scope.zip
                },
                inventory : $scope.items
            };

            var res = $http.post('/rest/job/new', newJob, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.jobNew = data;
                $scope.addedJob = true;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            $scope.name = '';
            $scope.client = '';
            $scope.startDate = '';
            $scope.status = '';
            $scope.jobPrice = '';
            $scope.street = '';
            $scope.city = '';
            $scope.state = '';
            $scope.zip = '';
            $scope.items = [];
            $scope.st = false;
        }

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }

    })

    .controller('listJobs', function($scope, $http, $filter) {
        $(document).ready(function(){
            var res = $http.get("/rest/job/list", authconfig);
            res.success(function(data, status, headers, config) {
                $scope.jobList = $filter('orderBy')(data, 'name');
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.selectJob = function(id){
            window.localStorage.setItem(jobId, id);
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    .controller('indvJob', function($scope, $http, $filter){
        $(document).ready(function(){
            console.log("Job page!" + localStorage.getItem(jobId));
            $scope.iForm = false;
            $scope.invFilter = "All";
            $scope.tasksLeft = 0;
            var url1 = "/rest/job/select?id=" + window.localStorage.getItem(jobId);
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.job = data;
                var tl = 0;
                for(i=0;i<data.tasks.length;i++){
                    if(!data.tasks[i].completed){
                        tl += 1;
                    }
                }
                $scope.tasksLeft = tl;
                $scope.totalRevenue = data.jobPrice.toFixed(2);
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            var res2 = $http.get("/rest/inventory/itemtype/list", authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url3 = "/rest/inventory/loc/find?locId=" + window.localStorage.getItem(jobId);
            var res3 = $http.get(url3, authconfig);
            res3.success(function(data, status, headers, config) {
                $scope.jobInv = data;
                var tc = 0;
                for(i=0;i<$scope.jobInv.length;i++){
                    tc += $scope.jobInv[i].totalCost;
                }
                $scope.jobTotalCost = tc.toFixed(2);
                $scope.filterInventory("All");
            });
            res3.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var res4 = $http.get("/rest/user/list", authconfig);
            res4.success(function(data, status, headers, config) {
                $scope.dbUsers = data;
            });
            res4.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.updateJobStatus = function(){
            var res = $http.get("/rest/job/progress/update?cjId=" + window.localStorage.jobId, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.job = data;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        }

        $scope.filterInventory = function(filter){
            if(filter === "All"){
                for(i=0;i<$scope.jobInv.length;i++){
                    $scope.jobInv[i].showCost = $scope.jobInv[i].totalCost.toFixed(2);
                }
                $scope.invToShow = $filter('orderBy')($scope.jobInv, ['name', 'status']);
            }else{
                $scope.invToShow = [];
                for(i=0;i<$scope.jobInv.length;i++){
                    $scope.jobInv[i].showCost = $scope.jobInv[i].totalCost.toFixed(2);
                    if($scope.jobInv[i].status === filter){
                        $scope.invToShow.push($scope.jobInv[i]);
                        $scope.invToShow = $filter('orderBy')($scope.invToShow, ['name', 'status'])
                    }
                }
            }
        };

        $scope.showIForm = function(){
            if($scope.wForm === true){
                $scope.wForm = false;
            }
            if($scope.tForm === true){
                $scope.tForm = false;
            }
            $scope.iForm = !($scope.iForm);

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            $scope.tTitle = '';
            $scope.tDesc = '';
            $scope.wUser = '';
            $scope.currLocInv = [];
            $scope.workerJobs = [];
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
            document.getElementById("tAddBtn").disabled = true;
            document.getElementById("wAddBtn").disabled = true;
        };

        $scope.showTForm = function(){
            if($scope.wForm === true){
                $scope.wForm = false;
            }
            if($scope.iForm === true){
                $scope.iForm = false;
            }
            $scope.tForm = !($scope.tForm);

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            $scope.tTitle = '';
            $scope.tDesc = '';
            $scope.wUser = '';
            $scope.currLocInv = [];
            $scope.workerJobs = [];
            document.getElementById("tAddBtn").disabled = true;
            document.getElementById("wAddBtn").disabled = true;
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.showWForm = function(){
            if($scope.tForm === true){
                $scope.tForm = false;
            }
            if($scope.iForm === true){
                $scope.iForm = false;
            }
            $scope.wForm = !($scope.wForm);

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            $scope.tTitle = '';
            $scope.tDesc = '';
            $scope.wUser = '';
            $scope.tForm = false;
            $scope.currLocInv = [];
            $scope.workerJobs = [];
            document.getElementById("tAddBtn").disabled = true;
            document.getElementById("wAddBtn").disabled = true;
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.findInv = function(){
            var url1 = "/rest/invholder/find?type=" + $scope.itType;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                var invhol = data;
                for(i=0;i<invhol.length;i++){
                    if(invhol[i].id === $scope.job.id){
                        invhol.splice(i, 1);
                    }
                }
                $scope.ihswt = invhol;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url2 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.job.name;
            var res2 = $http.get(url2, authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.currItem = data;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url3 = "/rest/inventory/type/loc/find?type=" + $scope.itType + "&locId=" + $scope.job.id;
            var res3 = $http.get(url3, authconfig);
            res3.success(function(data, status, headers, config) {
                $scope.currLocInv = data;
            });
            res3.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            document.getElementById("itFrom").disabled=false;
        };

        $scope.findQty = function(){
            $scope.qs = [];
            var url1 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.itFrom;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                var cibF = data;
                $scope.cibCheck = data;
                if($scope.itFrom === "New"){
                    for(j=0;j<30;j++){
                        $scope.qs.push(j+1);
                    }
                    $scope.maxQty = 100000;
                }else {
                    for (i = 0; i < cibF.quantity; i++) {
                        $scope.qs.push(i + 1);
                    }
                    $scope.maxQty = cibF.quantity;

                }
                $scope.uom = cibF.bucketType.unitOfMeasurement;
            });

            document.getElementById("itQty").disabled=false;
        };

        $scope.enableStatus = function (){
            document.getElementById("itStatus").disabled=false;
        };

        $scope.enableItAddBtn = function (){
            document.getElementById("itAddBtn").disabled=false;
        };

        $scope.addItem = function(){
            $scope.st = true;
            var exists = false;
            var index = 0;
            var newItem = {
                type : $scope.itType,
                qty : parseInt($scope.itQty),
                status : $scope.itStatus,
                from : $scope.itFrom,
                maxQty : $scope.maxQty,
                uOfM : $scope.uom
            };
            console.log("Add: " + newItem.type);

            var res1 = $http.post('/rest/job/inventory/new?cjId=' + window.localStorage.getItem(jobId), newItem, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.jobNew = data;
                var url2 = "/rest/job/select?id=" + window.localStorage.getItem(jobId);
                var res2 = $http.get(url2, authconfig);
                res2.success(function(data, status, headers, config) {
                    $scope.job = data;
                    var url3 = "/rest/inventory/loc/find?locId=" + window.localStorage.getItem(jobId);
                    var res3 = $http.get(url3, authconfig);
                    res3.success(function(data, status, headers, config) {
                        $scope.jobInv = $filter('orderBy')(data, ['name', 'status']);
                        var tc = 0;
                        for(i=0;i<$scope.jobInv.length;i++){
                            tc += $scope.jobInv[i].totalCost;
                        }
                        $scope.jobTotalCost = tc.toFixed(2);
                        $scope.filterInventory($scope.invFilter);
                    });
                });
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            $scope.currLocInv = [];
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.enableTAddBtn = function (){
            if($scope.tTitle != '' && $scope.tDesc != '') {
                document.getElementById("tAddBtn").disabled = false;
            }
        };

        $scope.addTask = function(){
            var newTask = {
                name: $scope.tTitle,
                body: $scope.tDesc,
                jobId: window.localStorage.getItem(jobId)
            };

            var res1 = $http.post('/rest/job/task/new', newTask, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.jobNew = data;
                var url2 = "/rest/job/select?id=" + window.localStorage.getItem(jobId);
                var res2 = $http.get(url2, authconfig);
                res2.success(function(data, status, headers, config) {
                    $scope.job = data;
                    var tl = 0;
                    for(i=0;i<data.tasks.length;i++){
                        if(!data.tasks[i].completed){
                            tl += 1;
                        }
                    }
                    $scope.tasksLeft = tl;
                });
            });
            res1.error(function(data, status, headers, config) {
            });

            $scope.tTitle = '';
            $scope.tDesc = '';
            $scope.tForm = false;
        };

        $scope.enableWAddBtn = function (){
            var url1 = "/rest/user/jobs?cuId=" + $scope.wUser;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.workerJobs = data;
            });
            document.getElementById("wAddBtn").disabled = false;
        };

        $scope.addWorker = function(){
            var url1 = "/rest/job/workers/add?cuId=" + $scope.wUser + "&cjId=" + $scope.job.id;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.job = data;
            });
            $scope.wUser = "";
            $scope.wForm = false;
            $scope.workerJobs = [];
        };

        $scope.markAsComplete = function(id){
            console.log("Marked task " + id + " as complete");
            var url1 = "/rest/job/task/completed?ctId=" + id + "&cjId=" + $scope.job.id;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.job = data;
                var tl = 0;
                for(i=0;i<data.tasks.length;i++){
                    if(!data.tasks[i].completed){
                        tl += 1;
                    }
                }
                $scope.tasksLeft = tl;
            });
        };

        $scope.deleteTask = function(id){
            console.log("Delete Task:" + id);
            var url1 = "/rest/job/task/delete?ctId=" + id + "&cjId=" + $scope.job.id;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.job = data;
                var tl = 0;
                for(i=0;i<data.tasks.length;i++){
                    if(!data.tasks[i].completed){
                        tl += 1;
                    }
                }
                $scope.tasksLeft = tl;
            });
        };

        $scope.updateItemStatus = function(id){
            var res = $http.get('/rest/inventory/item/update?id=' + id, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.job = data;
                var url3 = "/rest/inventory/loc/find?locId=" + window.localStorage.getItem(jobId);
                var res3 = $http.get(url3, authconfig);
                res3.success(function(data, status, headers, config) {
                    $scope.jobInv = $filter('orderBy')(data, ['name', 'status']);
                    var tc = 0;
                    for(i=0;i<$scope.jobInv.length;i++){
                        tc += $scope.jobInv[i].totalCost;
                    }
                    $scope.jobTotalCost = tc.toFixed(2);
                    $scope.filterInventory($scope.invFilter);
                });
                res3.error(function(data, status, headers, config) {
                    window.location.replace("/login.html");
                });
            });
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    // ==================
    // Items Controllers
    // ==================

    .controller('newItem', function($scope, $http) {
        $(document).ready(function(){
            var res1 = $http.get("/rest/job/list", authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.cjs = data;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var res2 = $http.get("/rest/inventory/itemtype/list", authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
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

            var res = $http.post('/rest/inventory/item/new', newItem, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.itemNew = data;
            });
            // res.error(function(data, status, headers, config) {
            //     window.location.replace("/login.html");
            // });

            $scope.qty = '';
            $scope.status = '';
            $scope.type = '';
            $scope.jLoc = '';
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }

    })

    .controller('listItems', function($scope, $http, $filter) {
        $(document).ready(function(){

            var res1 = $http.get("/rest/inventory/list", authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.itemList = $filter('orderBy')(data, 'bucketType.name');;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var res2 = $http.get("/rest/invholder/list", authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.jobList = data;
                var jNames = [];

                for(i=0;i<data.length;i++){
                    jNames[data[i].id] = data[i].name;
                }

                $scope.jn = jNames;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

        });

        $scope.selectItem = function(id){
            window.localStorage.setItem(itemId, id);
        };
    })

    .controller('indvItem', function($scope, $http){
        $(document).ready(function(){
            var url = "/rest/inventory/select?id=" + window.localStorage.getItem(itemId);
            var res = $http.get(url, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.it = data;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            window.localStorage.setItem(itemId, "");
        });

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        };
    })

    .controller('newType', function($scope, $http) {

        $scope.submitType = function(){
            var newType = {
                name : $scope.name,
                unitOfMeasurement : $scope.units,
                costPerUnit : $scope.cost
            };

            var res = $http.post('/rest/inventory/itemtype/new', newType, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.clientNew = data;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            $scope.name = '';
            $scope.units = '';
            $scope.cost = '';
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    .controller('listTypes', function($scope, $http, $filter) {
        $(document).ready(function(){
            var res = $http.get("/rest/inventory/itemtype/list", authconfig);
            res.success(function(data, status, headers, config) {
                $scope.itemTypes = $filter('orderBy')(data, 'name');
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.selectType = function(id){
            window.localStorage.setItem("typeId", id);
        };
    })

    .controller('editType', function($scope, $http) {
        $(document).ready(function(){
            var res = $http.get("/rest/inventory/itemtype/find?citId=" + window.localStorage.getItem("typeId"), authconfig);
            res.success(function(data, status, headers, config) {
                $scope.cit = data;
                $scope.name = $scope.cit.name;
                $scope.units = $scope.cit.unitOfMeasurement;
                $scope.cost = $scope.cit.costPerUnit;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.toggle = function(){
            var res = $http.get("/rest/inventory/itemtype/toggleActive?citId=" + window.localStorage.getItem("typeId"), authconfig);
            res.success(function(data, status, headers, config) {
                $scope.cit = data;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        };

    })

    // ==================
    // Users Controllers
    // ==================

    .controller('newUser', function($scope, $http) {
        $(document).ready(function(){
            console.log("New User Controller checking in!");
            $scope.addedUser = false;
        });

        $scope.readFile = function(){
            var f = document.getElementById('file').files[0],
                r = new FileReader();
            r.onloadend = function(e){
                var data = e.target.result;
                $scope.fileSeuccess = data;
            };
            // $scope.fileSuccess = r.readAsArrayBuffer(f);
            r.readAsBinaryString(f);
        };

        $scope.submitUser = function(){
            console.log("User Submitted!");

            var newUser = {
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                username: $scope.username,
                password: $scope.password,
                phoneNumber: $scope.phoneNumber,
                email: $scope.email,
                currentAddress : {
                    street : $scope.street,
                    city : $scope.city,
                    state : $scope.state,
                    zipCode : $scope.zip
                },
            };

            var res = $http.post('/rest/user/new', newUser, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.userNew = data;
                $scope.addedUser = true;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            $scope.firstName = '';
            $scope.lastName = '';
            $scope.username = '';
            $scope.password = '';
            $scope.email = '';
            $scope.phoneNumber = '';
            $scope.street = '';
            $scope.city = '';
            $scope.state = '';
            $scope.zip = '';


        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        };

    })

    .controller('listUsers', function($scope, $http, $filter) {

        $(document).ready(function(){
            var res = $http.get("/rest/user/list", authconfig);
            res.success(function(data, status, headers, config) {
                $scope.userList = $filter('orderBy')(data, 'lastName');
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.selectUser = function(id){
            window.localStorage.setItem(userId, id);
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        };
    })

    .controller('myProfile', function($scope, $http, $filter) {

        $(document).ready(function(){
            $scope.updatedUser = false;
            var res = $http.get("/rest/user/me", authconfig);
            res.success(function(data, status, headers, config) {
                $scope.uMe = data;
                $scope.id = data.id;
                $scope.firstName = data.firstName;
                $scope.lastName = data.lastName;
                $scope.username = data.username;
                $scope.password = '';
                $scope.street = data.currentAddress.street;
                $scope.city = data.currentAddress.city;
                $scope.zip = data.currentAddress.zipCode;
                $scope.state = data.currentAddress.state;
                $scope.email = data.email;
                $scope.phoneNumber = data.phoneNumber;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.submitUser = function(){
            console.log("User Submitted!");

            if($scope.password = '');
            {
                $scope.password = $scope.uMe.password;
            }

            var newUser = {
                id: $scope.id,
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                username: $scope.username,
                password: $scope.password,
                phoneNumber: $scope.phoneNumber,
                email: $scope.email,
                currentAddress : {
                    street : $scope.street,
                    city : $scope.city,
                    state : $scope.state,
                    zipCode : $scope.zip
                }
            };

            var res = $http.post('/rest/user/update', newUser, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.uMe = data;
                $scope.updatedUser = true;
                $scope.id = data.id;
                $scope.firstName = data.firstName;
                $scope.lastName = data.lastName;
                $scope.username = data.username;
                $scope.password = '';
                $scope.street = data.currentAddress.street;
                $scope.city = data.currentAddress.city;
                $scope.zip = data.currentAddress.zipCode;
                $scope.state = data.currentAddress.state;
                $scope.email = data.email;
                $scope.phoneNumber = data.phoneNumber;
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            $scope.firstName = '';
            $scope.lastName = '';
            $scope.username = '';
            $scope.password = '';
            $scope.email = '';
            $scope.phoneNumber = '';
            $scope.street = '';
            $scope.city = '';
            $scope.state = '';
            $scope.zip = '';


        };

        $scope.selectUser = function(id){
            window.localStorage.setItem(userId, id);
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        };
    })

    // ==================
    // Warehouses Controllers
    // ==================


    .controller('newWarehouse', function($scope, $http) {
        $(document).ready(function(){
            $scope.items = [];
            $scope.st = false;

            var res2 = $http.get("/rest/inventory/itemtype/list", authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            var res3 = $http.get("/rest/invholder/list", authconfig);
            res3.success(function(data, status, headers, config) {
                $scope.ihs = data;
            });
            res3.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

        });

        $scope.findInv = function(){
            var url1 = "/rest/invholder/find?type=" + $scope.itType;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.ihswt = data;
            });
            document.getElementById("itFrom").disabled=false;
        };

        $scope.findQty = function(){
            $scope.qs = [];
            var url1 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.itFrom;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                var cibF = data;
                $scope.cibCheck = data;
                if($scope.itFrom === "New"){
                    for(j=0;j<30;j++){
                        $scope.qs.push(j+1);
                    }
                    $scope.maxQty = 100000;
                }else {
                    for (i = 0; i < cibF.quantity; i++) {
                        $scope.qs.push(i + 1);
                    }
                    $scope.maxQty = cibF.quantity;

                }
                $scope.uom = cibF.bucketType.unitOfMeasurement;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            document.getElementById("itQty").disabled=false;
        };

        $scope.enableStatus = function (){
            document.getElementById("itStatus").disabled=false;
        };

        $scope.enableAddBtn = function (){
            document.getElementById("itAddBtn").disabled=false;
        };

        $scope.incQty = function(it){
            if($scope.items[it].qty < $scope.items[it].maxQty){
                $scope.items[it].qty += 1
            }
        };

        $scope.decQty = function(it){
            if($scope.items[it].qty > 1){
                $scope.items[it].qty -= 1
            } else if($scope.items[it].qty === 1){
                $scope.deleteItem(it);
            }
        };


        $scope.addItem = function(){
            $scope.st = true;
            var exists = false;
            var index = 0;
            var itemToAdd = {
                type : $scope.itType,
                qty : parseInt($scope.itQty),
                status : $scope.itStatus,
                from : $scope.itFrom,
                maxQty : $scope.maxQty,
                uOfM : $scope.uom
            };
            console.log("Add: " + itemToAdd.type);
            for(z=0;z<$scope.items.length;z++){
                console.log(z);

                if($scope.items[z].type === itemToAdd.type &&
                    $scope.items[z].status === itemToAdd.status &&
                    $scope.items[z].from === itemToAdd.from){
                    exists = true;
                    index = z;
                }
            }

            if(exists){
                $scope.items[index].qty = $scope.items[index].qty + itemToAdd.qty;
            }else {
                $scope.items.push(itemToAdd);
                $scope.items[$scope.items.length - 1].index = $scope.items.length - 1;
            }

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.deleteItem = function(ditem){
            console.log("Delete Item");
            var spot = ditem;
            $scope.items.splice(spot, 1);
            for(i=spot;i<$scope.items.length;i++){
                $scope.items[i].index = i;
            }
        };

        $scope.submitWarehouse = function(){
            console.log("Warehouse Submitted!");

            var newWarehouse = {
                name : $scope.name,
                address : {
                    street : $scope.street,
                    city : $scope.city,
                    state : $scope.state,
                    zipCode : $scope.zip
                },
                inventory : $scope.items
            };

            var res = $http.post('/rest/warehouse/new', newWarehouse, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.warehouseNew = data;
            });
            // res.error(function(data, status, headers, config) {
            //     window.location.replace("/login.html");
            // });

            $scope.name = '';
            $scope.street = '';
            $scope.city = '';
            $scope.state = '';
            $scope.zip = '';
            $scope.items = [];
            $scope.st = false;
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }

    })

    .controller('listWarehouses', function($scope, $http, $filter) {
        $(document).ready(function(){
            var res = $http.get("/rest/warehouse/list", authconfig);
            res.success(function(data, status, headers, config) {
                $scope.warehouseList = $filter('orderBy')(data, 'name');
            });
            res.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.selectWarehouse = function(id){
            window.localStorage.setItem("warehouseId", id);
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    .controller('indvWarehouse', function($scope, $http, $filter){
        $(document).ready(function(){
            console.log("Warehouse Page: " + window.localStorage.getItem("warehouseId"));
            $scope.iForm = false;
            $scope.invFilter = "All";
            var url1 = "/rest/warehouse/select?id=" + window.localStorage.getItem("warehouseId");
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.warehouse = data;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            var res2 = $http.get("/rest/inventory/itemtype/list", authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url3 = "/rest/inventory/loc/find?locId=" + window.localStorage.getItem("warehouseId");
            var res3 = $http.get(url3, authconfig);
            res3.success(function(data, status, headers, config) {
                $scope.wInv = data;
                $scope.filterInv("All");
            });
            res3.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
        });

        $scope.toggle = function() {
            var res = $http.get("/rest/warehouse/toggle/active?id=" + window.localStorage.getItem("warehouseId"), authconfig);
            res.success(function (data, status, headers, config) {
                $scope.warehouse = data;
            });
            res.error(function (data, status, headers, config) {
                window.location.replace("/login.html");
            });
        }

        $scope.filterInv = function(filter){
            console.log("Filter Inventory called");
            if(filter === "All"){
                $scope.invToShow = $filter('orderBy')($scope.wInv, ['name', 'status']);
            }else{
                $scope.invToShow = [];
                for(i=0;i<$scope.wInv.length;i++){
                    if($scope.wInv[i].status === filter){
                        $scope.invToShow.push($scope.wInv[i]);
                        $scope.invToShow = $filter('orderBy')($scope.invToShow, ['name', 'status'])
                    }
                }
            }
        };

        $scope.showIForm = function(){
            $scope.iForm = !($scope.iForm);

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            $scope.tTitle = '';
            $scope.tDesc = '';
            $scope.wUser = '';
            $scope.currLocInv = [];
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.findInv = function(){
            var url1 = "/rest/invholder/find?type=" + $scope.itType;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                var invhol = data;
                for(i=0;i<invhol.length;i++){
                    if(invhol[i].id === $scope.warehouse.id){
                        invhol.splice(i, 1);
                    }
                }
                $scope.ihswt = invhol;
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url2 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.warehouse.name;
            var res2 = $http.get(url2, authconfig);
            res2.success(function(data, status, headers, config) {
                $scope.currItem = data;
            });
            res2.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });
            var url3 = "/rest/inventory/type/loc/find?type=" + $scope.itType + "&locId=" + $scope.warehouse.id;
            var res3 = $http.get(url3, authconfig);
            res3.success(function(data, status, headers, config) {
                $scope.currLocInv = data;
            });
            res3.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            document.getElementById("itFrom").disabled=false;
        };

        $scope.findQty = function(){
            $scope.qs = [];
            var url1 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.itFrom;
            var res1 = $http.get(url1, authconfig);
            res1.success(function(data, status, headers, config) {
                var cibF = data;
                $scope.cibCheck = data;
                if($scope.itFrom === "New"){
                    for(j=0;j<30;j++){
                        $scope.qs.push(j+1);
                    }
                    $scope.maxQty = 100000;
                }else {
                    for (i = 0; i < cibF.quantity; i++) {
                        $scope.qs.push(i + 1);
                    }
                    $scope.maxQty = cibF.quantity;

                }
                $scope.uom = cibF.bucketType.unitOfMeasurement;
            });

            document.getElementById("itQty").disabled=false;
        };

        $scope.enableStatus = function (){
            document.getElementById("itStatus").disabled=false;
        };

        $scope.enableItAddBtn = function (){
            document.getElementById("itAddBtn").disabled=false;
        };

        $scope.addItem = function(){
            $scope.st = true;
            var exists = false;
            var index = 0;
            var newItem = {
                type : $scope.itType,
                qty : parseInt($scope.itQty),
                status : $scope.itStatus,
                from : $scope.itFrom,
                maxQty : $scope.maxQty,
                uOfM : $scope.uom
            };
            console.log("Add: " + newItem.type);

            var res1 = $http.post('/rest/job/inventory/new?cjId=' + window.localStorage.getItem("warehouseId"), newItem, authconfig);
            res1.success(function(data, status, headers, config) {
                $scope.warehouseNew = data;
                var url2 = "/rest/warehouse/select?id=" + window.localStorage.getItem("warehouseId");
                var res2 = $http.get(url2, authconfig);
                res2.success(function(data, status, headers, config) {
                    $scope.warehouse = data;
                    var url3 = "/rest/inventory/loc/find?locId=" + window.localStorage.getItem("warehouseId");
                    var res3 = $http.get(url3, authconfig);
                    res3.success(function(data, status, headers, config) {
                        $scope.wInv = $filter('orderBy')(data, ['name', 'status']);
                        $scope.filterInv($scope.invFilter);
                    });
                });
            });
            res1.error(function(data, status, headers, config) {
                window.location.replace("/login.html");
            });

            $scope.itType = '';
            $scope.itQty = '';
            $scope.itStatus = '';
            $scope.itFrom = '';
            $scope.maxQty = '';
            $scope.qs = '';
            $scope.ihswt = '';
            $scope.currLocInv = [];
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.updateItemStatus = function(id){
            var res = $http.get('/rest/inventory/item/update?id=' + id, authconfig);
            res.success(function(data, status, headers, config) {
                $scope.warehouse = data;
                var url3 = "/rest/inventory/loc/find?locId=" + window.localStorage.getItem("warehouseId");
                var res3 = $http.get(url3, authconfig);
                res3.success(function(data, status, headers, config) {
                    $scope.wInv = $filter('orderBy')(data, ['name', 'status']);
                    $scope.filterInv($scope.invFilter);
                });
                res3.error(function(data, status, headers, config) {
                    window.location.replace("/login.html");
                });
            });
        };

        $scope.logout = function (){
            window.localStorage.removeItem("token");
            window.location.replace("/login.html");
        }
    })

    .service('dataService', function($http){
        this.inProgressJobs = function(callback){
            $http.get("/rest/job/list/inprogress", authconfig)
                .then(callback)
        };

        this.listOfClients = function(){
            $http.get("/rest/client/list", authconfig)
        };

        this.clientSubmit = function(){
            $http.post("/rest/client/new", data, authconfig)
                .then(successCallback, errorCallback);
        }
    });

