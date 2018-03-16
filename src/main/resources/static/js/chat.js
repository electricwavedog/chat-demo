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
            context.append('<p align="right">' + body.message + ' :' + body.fromUser.username + '</p>')
        } else {
            context.append('<p align="left">' + body.fromUser.username + ': ' + body.message + '</p>')
        }
        context.scrollTop(context[0].scrollHeight);
    }

    $('#send').click(function () {
        var message = $('#message').val();
        if (message == '') {
            return;
        }
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
    })
});