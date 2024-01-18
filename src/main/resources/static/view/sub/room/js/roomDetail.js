var rootName = "room";
var parentformRoom = {};
parentformRoom.type = "Unit";
var IDformRoom = {};
var RoomTypeform = {};
RoomTypeform.type = "Room"
var strucRoomID;
var strucProjectID;



$(document).ready(function () {
    // 클릭한 위치 active 적용
    //$("#room").addClass('active');
    onLoadedRoom();
    timestamp();
});

function onLoadedRoom(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 projectId 추출, unique ID 생성
    var roomId = $("#txt_roomId").val();

    var uniqueId;
    if (roomId !== "" && roomId !== "${id}" && roomId !== undefined) {
        uniqueId = "_" + roomId;
    } else if (roomId === "") {
        // create 인 경우
        uniqueId = "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경,   데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('roomPropertyForm', uniqueId);

    if (isFirst === true){
        LoadRoomParentId(uniqueId)
        LoadRoomParent(uniqueId);
        LoadRoomID(uniqueId);
        LoadProjectID(uniqueId)
        dg_projectLoadData(uniqueId);


        // button event
        $("#btn_room_save" + uniqueId).on("click", function(e) {
            dg_roomSaveExecute(uniqueId);
        });

        $("#btn_room_import" + uniqueId).on("click", function(e) {
            dg_roomImportExecute(uniqueId);
        });
    }
}
/*function LoadRoomId(uniqueId) {
//structure 리스트
    $.ajax({
        type: 'POST',
        url: "/getStructureList",
        data: JSON.stringify(idformData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
        },
        success: function (data) {
            var parentSelect = $('#room_parent' + uniqueId);
            data.forEach(item => {
                var parentid = $("#txt_parentId"+ uniqueId).val();

                if (parentid == item.objectID)
                {
                    parentSelect.append(new Option(item.name, item.objectID, true, true));
                }
                else{
                    parentSelect.append(new Option(item.name, item.objectID, false, false));
                }

            });
        }
    });
}*/

function LoadRoomParentId(uniqueId)
{
    var roomIntId = $('#txt_roomId'+ uniqueId).val();

    RoomTypeform.objectID =  parseInt(roomIntId);

    $.ajax({
        type: 'POST',
        url: "/getStructureParentID",
        data: JSON.stringify(RoomTypeform),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
            alert("error")
        },
        success: function (data) {
            data.forEach(item => {
                strucpicker = item.parentID;
            });

        }
    }).done(function (fragment) {

    });

}
function LoadRoomID(uniqueId) {

    var roomIntId = $('#txt_roomId'+ uniqueId).val();

    IDformRoom.objectID =  parseInt(roomIntId);
    IDformRoom.type = "Room";

    $.ajax({
        type: 'POST',
        url: "/getStructureID",
        data: JSON.stringify(IDformRoom),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {

        },
        success: function (data) {
            data.forEach(item => {
                strucRoomID = item.id;
            });

        }
    }).done(function (fragment) {

    });
}

function LoadProjectID(uniqueId) {

    var projectIntId = $('#txt_projectId'+ uniqueId).val();

    IDformRoom.objectID =  parseInt(projectIntId);
    IDformRoom.type = "Project";

    $.ajax({
        type: 'POST',
        url: "/getStructureID",
        data: JSON.stringify(IDformRoom),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {

        },
        success: function (data) {
            data.forEach(item => {
                strucProjectID = item.id;
                console.log(strucProjectID)
            });


        }
    }).done(function (fragment) {

    });
}
function LoadRoomParent(uniqueId) {
//structure 리스트
    $.ajax({
        type: 'POST',
        url: "/getStructureType",
        data: JSON.stringify(parentformRoom),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
        },
        success: function (data) {
            var parentSelect = $('#room_parent' + uniqueId);
            const tempList = data.sort((a,b) => {
                if(a.name > b.name) return 1;
                if(a.name < b.name) return -1;
                return 0;
            });
            tempList.forEach(item => {
                var parentid = $("#txt_parentId"+ uniqueId).val();
                if (strucpicker == item.objectID)
                {
                    parentSelect.append(new Option(item.name, item.objectID, true, true));
                }
                else{
                    parentSelect.append(new Option(item.name, item.objectID, false, false));
                }

            });
        }
    });
}

function dg_roomSaveExecute(uniqueId){
    var structureform = {};
    structureform.id = strucRoomID;
    structureform.name = $('#txt_name' + uniqueId).val();
    structureform.objectID = $('#txt_roomId' + uniqueId).val();
    structureform.parentID = $("#room_parent"+ uniqueId).val();
    structureform.description = $('#txt_description' + uniqueId).val();

    var formData = {};
    formData.name = $('#txt_name' + uniqueId).val();
    formData.id = $('#txt_roomId' + uniqueId).val();
    formData.operator = $('#txt_operator' + uniqueId).val();
    formData.description = $('#txt_description' + uniqueId).val();
    formData.projectID = $('#room_project' + uniqueId).val();
    formData.projectName = $("#room_project"+ uniqueId +" option:checked").text().trim();


    $.ajax({
        url : "/structureUpdate",
        type: 'POST',
        async: false,
        data: JSON.stringify(structureform),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            projcetstrucSave(uniqueId)
            $.ajax({
                url : "/roomUpdate",
                type: 'POST',
                async: false,
                data: JSON.stringify(formData),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success : function(data) {
                    //location.href = "roomDetail?" + "id=" + data.result.id;
                    alert('저장이 완료 되었습니다.');
                    // 리스트 리로드
                    dg_roomReloadExecute();

                },
                error : function(data) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            });
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function projcetstrucSave(uniqueId){
    var structureform = {};
    structureform.id = strucProjectID;
    structureform.name =$("#room_project"+ uniqueId +" option:checked").text().trim();
    structureform.parentID = $('#txt_roomId'+ uniqueId).val();
    structureform.objectID = $("#room_project"+ uniqueId).val();

    $.ajax({
        url : "/structureUpdate",
        type: 'POST',
        async: false,
        data: JSON.stringify(structureform),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            if (strucProjectID == null)
            {
                alert("project id오류");
                LoadProjectID(uniqueId);

            }
            else
            {
                alert("성공")
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_roomImportExecute(uniqueId){
    var formData = {};
    formData.name = $('#txt_name' + uniqueId).val();
    formData.id = $('#txt_roomId' + uniqueId).val();
    formData.operator = $('#txt_operator' + uniqueId).val();
    formData.description = $('#txt_description' + uniqueId).val();
    formData.projectID = $('#room_project' + uniqueId).val();


    $.ajax({
        url : "/roomUpdate",
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            //location.href = "roomDetail?" + "id=" + data.result.id;
            alert('저장이 완료 되었습니다.');
            // 리스트 리로드
            dg_roomReloadExecute();

        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_roomDeleteExecute(uniqueId){
    /* var structureform = {};
    structureform.id = strucRoomID;

    var formData = {};
    formData.id = $('#txt_roomId' + uniqueId).val();
    $.ajax({
        url : "/structureDelete",
        type: 'DELETE',
        async: false,
        data: JSON.stringify(structureform),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            $.ajax({
                url : "/roomDelete",
                type: 'DELETE',
                async: false,
                data: JSON.stringify(formData),
                processData: false,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success : function(data) {
                    //location.href = "roomList";
                    alert('삭제가 완료 되었습니다.');

                    // 해당 패널 닫기
                    var roomDetailContainer = myLayout.root.getItemsById('roomDetail_' + $('#txt_roomId' + uniqueId).val());
                    if (roomDetailContainer !== undefined) {
                        roomDetailContainer[0].close();
                    }

                    // 리스트 리로드
                    dg_roomReloadExecute();
                },
                error : function(data) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            });
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });*/
}


function timestamp() {
    var today = new Date();
    today.setHours(today.getHours() + 9);
    return today.toISOString().replace('T', ' ').substring(0, 19);
}

function historyBack() {
    //window.history.back();
    location.href = "roomList";
}




function dg_projectLoadData(uniqueId) {
    $.ajax({
        url : "/getProjectList",
        type: 'POST',
        async: false,
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function(result) {
            var projectSelect = $('#room_project' + uniqueId);
            var projectid = $("#txt_projectId"+ uniqueId).val();

            if(projectid == "null")
            {
                projectSelect.append(new Option("null",null,false,true))
            }

            result.forEach(item => {
                if (projectid == `null` ){
                    if (projectid == item.id)
                    {
                        projectSelect.append(new Option(item.name, item.id, false, false));
                        /*$("#room_project option[value=projectid]").remove();*/
                    }
                    else{
                        projectSelect.append(new Option(item.name, item.id, false, false));
                    }
                }
                else
                {
                    if (projectid == item.id)
                    {
                        projectSelect.append(new Option(item.name, item.id, false, true));
                        /*$("#room_project option[value=projectid]").remove();*/
                    }
                    else{
                        projectSelect.append(new Option(item.name, item.id, false, false));

                    }
                }
            });
        },
        error: function(result) {
            options.error(result);
        }
    });
}
