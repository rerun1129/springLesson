const main = {
    init : function () {
        const _this = this
        $( '#btn-save' ).on( 'click', function () {
            _this.save();
        } )

        $( '#btn-update' ).on( 'click', function () {
            _this.update();
        } )

        $( '#btn-delete' ).on( 'click', function () {
            _this.delete();
        } )
    },
    save : function ()  {
        const data = {
            title   : $( '#title' ).val(),
            author  : $( '#author' ).val(),
            contents : $( '#contents' ).val()
        }

        $.ajax( {

            type        : 'POST',
            url         : '/api/posts',
            contentType : 'application/json; charset=utf-8',
            data        : JSON.stringify( data )//json 데이터를 문자열화

        } ).done( function () {
            alert( '글이 등록되었습니다.' )
            window.location.href = '/posts'; //요청과 응답이 성공했을 경우
        } ).fail( function ( error ) {
            alert( JSON.stringify( error ) ); //실패하면 에러 메세지
        } )
    },

    update : function () {
        const data = {
            title   : $( '#title' ).val(),
            contents : $( '#contents' ).val()
        }
        const id = $( '#id' ).val()

        $.ajax( {
            type        : 'PUT',
            url         : '/api/posts/' + id,
            contentType : 'application/json; charset=utf-8',
            data        : JSON.stringify( data )
        } ).done( function () {
            alert( '글이 수정되었습니다.' )
            window.location.href = '/posts';
        } ).fail( function ( error ) {
            alert( JSON.stringify( error ) )
        } )

    },

    delete : function () {
        const id = $( '#id' ).val()

        $.ajax( {
            type        : 'DELETE',
            url         : '/api/posts/' + id,
            contentType : 'application/json; charset=utf-8'
        } ).done( function () {
            alert( '글이 삭제되었습니다.' )
            window.location.href = '/posts'
        } ).fail( function ( error ) {
            alert( JSON.stringify( error ) )
        } )
    }

}

main.init();