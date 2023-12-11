var rootName = "room";

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
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('roomPropertyForm', uniqueId);

    if (isFirst === true){
        var editMode = $("#txt_editMode" + uniqueId).val();

        // button event
        $("#btn_room_save" + uniqueId).on("click", function(e) {
            dg_roomSaveExecute(uniqueId);
        });

        $("#btn_room_delete" + uniqueId).on("click", function(e) {
            dg_roomDeleteExecute(uniqueId);
        });
    }
}

function dg_roomSaveExecute(uniqueId){
    var url;
    var formData = {};
    formData.name = $('#txt_namevalue' + uniqueId).val();
    formData.id = $('#txt_roomId' + uniqueId).val();

    url = "/roomUpdate";

    $.ajax({
        url : url,
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

            // 저장된 데이터가 신규 인 경우
            var newRoomContainer = myLayout.root.getItemsById('roomDetail_newItem');

            if (newRoomContainer !== undefined) {
                var oldId = "_newItem";

                $('#btn_room_save' + oldId).off("click");
                $('#btn_room_delete' + oldId).off("click");

                var roomId = data.result.id;
                newRoomContainer[0].setTitle(formData.name);
                newRoomContainer[0].config.id = 'roomDetail_' + roomId;

                var newId = "_" + roomId;

                $('#txt_roomId' + oldId).val(roomId);

                replaceFormNewId('roomPropertyForm', oldId, newId);

                // button event
                $("#btn_room_save" + newId).on("click", function(e) {
                    dg_roomSaveExecute(newId);
                });

                $("#btn_room_delete" + newId).on("click", function(e) {
                    dg_roomDeleteExecute(newId);
                });
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_roomDeleteExecute(uniqueId){
    var formData = {};
    formData.id = $('#txt_roomId' + uniqueId).val();

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

function dataGridSaveExecute() {

    var strucData = {};

    strucData.id = $('#txt_strucid').val();
    strucData.name = $('#txt_name').val();
    strucData.description = $('#txt_description').val();


    var url;
    var formData = {};
    formData.name = $('#txt_namevalue').val();
    url = "/roomUpdate";
    formData.id = $('#txt_id').val();


}

function dataGridDeleteExecute() {
    if (confirm("해당 아이템을 삭제 하시겠습니까?")) {

        var strucData = {};

        strucData.id = $('#txt_strucid').val();


        var formData = {};
        formData.id = $('#txt_id').val();


    }
}

function dg_roomReloadExecute(){
    $('#dg_room').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_room').data('kendoGrid').refresh(); <!--  refresh current UI -->
}