var clientId = 'clientId';
var jobId = 'jobId';
var itemId = 'itemId';

var authconfig = {
    headers: {
        'x-authorization-key': 'test',
    }
};

angular.module("app", [])


    // ==================
    // Index Controller
    // ==================

    .controller('home', function($scope, $http, $location) {

        $(document).ready(function(){
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
    })

    // ==================
    // Clients Controllers
    // ==================

    .controller('newClient', function($scope, $http) {
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

            var res = $http.post('/rest/client/new', newClient);
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
            var res = $http.get("/rest/client/list");
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
            $scope.tr = 0;

            var url1 = "/rest/client/select?id=" + window.localStorage.getItem(clientId);
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
               $scope.cl = data;
            });
            var url2 = "/rest/client/jobs?id=" + window.localStorage.getItem(clientId) + "&s=In Progress" ;
            var res2 = $http.get(url2);
            res2.success(function(data, status, headers, config) {
                $scope.jobListIP = data;
                var jlip = data;
                for(i=0;i<jlip.length;i++){
                    console.log(jlip[i].jobPrice);
                    $scope.tr = $scope.tr + jlip[i].jobPrice;
                }

            });
            var url3 = "/rest/client/jobs?id=" + window.localStorage.getItem(clientId) + "&s=Completed" ;
            var res3 = $http.get(url3);
            res3.success(function(data, status, headers, config) {
                $scope.jobListC = data;
                var jlc = data;
            });

            $scope.jobFromClient = function(id){
                window.localStorage.setItem(jobId, id);
            };

        })
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

            var res1 = $http.get("/rest/client/list");
            res1.success(function(data, status, headers, config) {
                $scope.clients = data;
            });

            var res2 = $http.get("/rest/inventory/itemtype/list");
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });

            var res3 = $http.get("/rest/invholder/list");
            res3.success(function(data, status, headers, config) {
                $scope.ihs = data;
            });

        });

        $scope.findInv = function(){
            var url1 = "/rest/invholder/find?type=" + $scope.itType;
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
                $scope.ihswt = data;
            });
            document.getElementById("itFrom").disabled=false;
        };

        $scope.findQty = function(){
            $scope.qs = [];
            var url1 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.itFrom;
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
                var cibF = data;
                $scope.cibCheck = data;
                console.log($scope.itFrom === "New");
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

            var res = $http.post('/rest/job/new', newJob);
            res.success(function(data, status, headers, config) {
                $scope.jobNew = data;
            });
            res.error(function(data, status, headers, config) {
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

    })

    .controller('listJobs', function($scope, $http) {
        $(document).ready(function(){
            var res = $http.get("/rest/job/list");
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
            $scope.iForm = false;
            $scope.invFilter = "All";
            var url1 = "/rest/job/select?id=" + window.localStorage.getItem(jobId);
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
                $scope.job = data;
            });
            var res2 = $http.get("/rest/inventory/itemtype/list");
            res2.success(function(data, status, headers, config) {
                $scope.cits = data;
            });
            var url3 = "/rest/inventory/loc/find?locId=" + window.localStorage.getItem(jobId);
            var res3 = $http.get(url3);
            res3.success(function(data, status, headers, config) {
                $scope.jobInv = data;
                var tc = 0;
                for(i=0;i<$scope.jobInv.length;i++){
                    tc += $scope.jobInv[i].totalCost;
                }
                $scope.jobTotalCost = tc;
                $scope.filterInventory("All");
            });
            var res4 = $http.get("/rest/user/list");
            res4.success(function(data, status, headers, config) {
                $scope.dbUsers = data;
            });
        });

        $scope.filterInventory = function(filter){
            if(filter === "All"){
                $scope.invToShow = $scope.jobInv;
            }else{
                $scope.invToShow = [];
                for(i=0;i<$scope.jobInv.length;i++){
                    if($scope.jobInv[i].status === filter){
                        $scope.invToShow.push($scope.jobInv[i]);
                    }
                }
            }
        }

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
            document.getElementById("tAddBtn").disabled = true;
            document.getElementById("wAddBtn").disabled = true;
            document.getElementById("itFrom").disabled=true;
            document.getElementById("itQty").disabled=true;
            document.getElementById("itStatus").disabled=true;
            document.getElementById("itAddBtn").disabled=true;
        };

        $scope.findInv = function(){
            var url1 = "/rest/invholder/find?type=" + $scope.itType;
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
                var invhol = data;
                for(i=0;i<invhol.length;i++){
                    if(invhol[i].id === $scope.job.id){
                        invhol.splice(i, 1);
                    }
                }
                $scope.ihswt = invhol;
            });
            var url2 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.job.name;
            var res2 = $http.get(url2);
            res2.success(function(data, status, headers, config) {
                $scope.currItem = data;
            });

            var url3 = "/rest/inventory/type/loc/find?type=" + $scope.itType + "&locId=" + $scope.job.id;
            var res3 = $http.get(url3);
            res3.success(function(data, status, headers, config) {
                $scope.currLocInv = data;
            });

            document.getElementById("itFrom").disabled=false;
        };

        $scope.findQty = function(){
            $scope.qs = [];
            var url1 = "/rest/inventory/find?type=" + $scope.itType + "&from=" + $scope.itFrom;
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
                var cibF = data;
                $scope.cibCheck = data;
                console.log($scope.itFrom === "New");
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
            var itemToAdd = {
                type : $scope.itType,
                qty : parseInt($scope.itQty),
                status : $scope.itStatus,
                from : $scope.itFrom,
                maxQty : $scope.maxQty,
                uOfM : $scope.uom
            };
            console.log("Add: " + itemToAdd.type);

            // POST ITEM CODE HERE

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

            var res1 = $http.post('/rest/job/task/new', newTask);
            res1.success(function(data, status, headers, config) {
                $scope.jobNew = data;
                var url2 = "/rest/job/select?id=" + window.localStorage.getItem(jobId);
                var res2 = $http.get(url2);
                res2.success(function(data, status, headers, config) {
                    $scope.job = data;
                });
            });
            res1.error(function(data, status, headers, config) {
            });

            $scope.tTitle = '';
            $scope.tDesc = '';
            $scope.tForm = false;
        };

        $scope.enableWAddBtn = function (){
            document.getElementById("wAddBtn").disabled = false;
        };

        $scope.addWorker = function(){
            var url1 = "/rest/job/workers/add?cuId=" + $scope.wUser + "&cjId=" + $scope.job.id;
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
                $scope.wUserSuccess = data;
            });
            $scope.wUser = "";
            $scope.wForm = false;
        }

        $scope.markAsComplete = function(id){
            console.log("Marked task " + id + " as complete");
            var url1 = "/rest/job/task/completed?ctId=" + id + "&cjId=" + $scope.job.id;
            var res1 = $http.get(url1);
            res1.success(function(data, status, headers, config) {
                $scope.job = data;
            });
        }
    })

    // ==================
    // Items Controllers
    // ==================

    .controller('newItem', function($scope, $http) {
        $(document).ready(function(){
            var res1 = $http.get("/rest/job/list");
            res1.success(function(data, status, headers, config) {
                $scope.cjs = data;
            });
            var res2 = $http.get("/rest/inventory/itemtype/list");
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

            var res1 = $http.get("/rest/inventory/list");
            res1.success(function(data, status, headers, config) {
                $scope.itemList = data;
            });
            var res2 = $http.get("/rest/job/list");
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
            var url = "/rest/inventory/select?id=" + window.localStorage.getItem(itemId);
            var res = $http.get(url);
            res.success(function(data, status, headers, config) {
                $scope.it = data;
            });
            window.localStorage.setItem(itemId, "");
        })
    })

    .controller('newType', function($scope, $http) {

        $scope.submitType = function(){
            console.log("Type Submitted!");


            var newType = {
                name : $scope.name,
                unitOfMeasurement : $scope.units,
                costPerUnit : $scope.cost
            };

            var res = $http.post('/rest/inventory/itemtype/new', newType);
            res.success(function(data, status, headers, config) {
                $scope.clientNew = data;
            });
            res.error(function(data, status, headers, config) {
            });

            $scope.name = '';
            $scope.units = '';
            $scope.cost = '';
        }

    })

    .controller('listTypes', function($scope, $http) {
        $(document).ready(function(){
            var res = $http.get("/rest/inventory/itemtype/list");
            res.success(function(data, status, headers, config) {
                $scope.itemTypes = data;
            });
        });

        $scope.selectType = function(id){
            window.localStorage.setItem(typeId, id);
        };
    })

    // ==================
    // User Controllers
    // ==================

    .controller('newUser', function($scope, $http) {
        $(document).ready(function(){
            console.log("New User Controller checking in!");
        });

        $scope.readFile = function(){
            var f = document.getElementById('file').files[0],
                r = new FileReader();
            r.onloadend = function(e){
                var data = e.target.result;
                $scope.fileSeuccess = data;
            }
            // $scope.fileSuccess = r.readAsArrayBuffer(f);
            r.readAsBinaryString(f);
        }

        $scope.submitUser = function(){
            console.log("User Submitted!");
            $scope.readFile();

            var newUser = {
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                username: $scope.username,
                password: $scope.password,
                contactEmail: $scope.cEmail,
                phoneNumber: $scope.phoneNumber,
                email: $scope.email,
                currentAddress : {
                    street : $scope.street,
                    city : $scope.city,
                    state : $scope.state,
                    zipCode : $scope.zip
                },
                file: $scope.fileSuccess
            };

            var res = $http.post('/rest/user/new', newUser);
            res.success(function(data, status, headers, config) {
                $scope.userNew = data;
            });
            res.error(function(data, status, headers, config) {
            });

            // $scope.name = '';
            // $scope.fn = '';
            // $scope.ln = '';
            // $scope.phone = '';
            // $scope.cEmail = '';
            // $scope.street = '';
            // $scope.city = '';
            // $scope.state = '';
            // $scope.zip = '';


        }

    })

    .controller('listUsers', function($scope, $http) {

        $(document).ready(function(){
            var res = $http.get("/rest/user/list");
            res.success(function(data, status, headers, config) {
                $scope.userList = data;
            });
        });

        $scope.selectUser = function(id){
            window.localStorage.setItem(userId, id);
        };
    })



    .service('dataService', function($http){
        this.inProgressJobs = function(callback){
            $http.get("/rest/job/list/inprogress")
                .then(callback)
        };

        this.listOfClients = function(){
            $http.get("/rest/client/list")
        };

        this.clientSubmit = function(){
            $http.post("/rest/client/new", data)
                .then(successCallback, errorCallback);
        }
    });

