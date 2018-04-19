$(function () {
    var socket = new SockJS('/endpoint');
    var stompClient = Stomp.over(socket);

    stompClient.connect({},
        function (frame) {
            console.log('Connected:' + frame);

            if (recipient != null & recipient != '') {
                stompClient.subscribe('/user/topic/private', function (response) {
                    setContect(JSON.parse(response.body));
                });
            } else {
                stompClient.subscribe('/topic/accept', function (response) {
                    setContect(JSON.parse(response.body));
                });
                stompClient.subscribe('/topic/image', function (response) {
                    setContect(JSON.parse(response.body));
                });

                stompClient.send("/notify");
                stompClient.subscribe('/topic/online', function (response) {
                    var context = $('.content');
                    context.append('<p align="center" style="color:darkgrey;">' + response.body + ' online' + '</p>')
                });
            }

        });

    function setContect(body) {
        var context = $('.content');
        if (username == body.fromUser.username) {
            context.append('<p align="right">' + body.fromUser.username + ' (' + body.sendTime + ') : ' + '</p>');
            if (body.imgPath == null || body.imgPath == '') {
                context.append('<p align="right">' + body.message + '</p>');
            } else {
                context.append('<a href="files/' + body.imgPath + '" target="_blank"><img src="files/' + body.imgPath + '" height="200" align="right" id="imgFile"/></a>');
            }
        } else {
            context.append('<p align="left">' + body.fromUser.username + ' (' + body.sendTime + ') : ' + '</p>');
            if (body.imgPath == null || body.imgPath == '') {
                context.append('<p align="left">' + body.message + '</p>');
            } else {
                context.append('<a href="files/' + body.imgPath + '" target="_blank"><img src="files/' + body.imgPath + '" height="200" align="left" id="imgFile"/></a>');
            }
        }
        context.scrollTop(context[0].scrollHeight);
    }

    $('#send').click(function () {
        var message = $('#message').val();

        // var inputElement = document.getElementById("file");
        // var fileList = inputElement.files;
        // var file=fileList[0];
        // var reader = new FileReader();
        // reader.readAsArrayBuffer(file);
        // reader.onload = function loaded(evt) {
        //     var blob = evt.target.result;
        //     stompClient.send('/send', {}, blob);
        // }

        var image = $('#img').val();

        if (message == '' && image =='') {
            return;
        }

        if (image != '') {
            var form = new FormData(document.getElementById('inputForm'));
            $.ajax({
                url: '/image',
                type: 'post',
                data: form,
                processData: false,
                contentType: false,
                success: function (result) {
                    console.log("upload success");
                }
            });
            $('#img').val('');
        }

        if (message != '') {
            var url;
            if (recipient != null & recipient != '') {
                url = '/private';
                var context = $('.content');
                context.append('<p align="right">' + message + ' :' + username + '</p>')
            } else {
                url = '/send';
            }
            stompClient.send(url, {}, JSON.stringify(
                {
                    'message': message,
                    'recipient': recipient
                })
            );
            $('#message').val('');
        }
    });

    $('#plane img').lightBox({});
});