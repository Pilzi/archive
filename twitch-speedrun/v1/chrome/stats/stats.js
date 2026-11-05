const clinetId = "YOUR_CLIENT_ID";
const clinetSecret = "YOUR_CLIENT_SECRET";


function getTwitchAuthorization() {

    let url = `https://id.twitch.tv/oauth2/token?client_id=${clinetId}&client_secret=${clinetSecret}&grant_type=client_credentials`;

    return fetch(url, {
        method: "POST",
        })
        .then((res) => res.json())
        .then((data) => {
            return data;
        });
    }
    




async function getData(endpoint) {

  let authorizationObject = await getTwitchAuthorization();
  let { access_token, expires_in, token_type } = authorizationObject;    

  token_type = token_type.charAt(0).toUpperCase() + token_type.slice(1);
  let authorization = `${token_type} ${access_token}`;

  let headers = {
  authorization,
  "Client-Id": clinetId,
  };
  console.log(headers)
  return fetch(endpoint, {
  headers,
  })
  .then((res) => res.json())
  .then((data) => renderData(data));
}

function renderData(data) {
  return data
}

window.onload = (event) => {



    
    chrome.storage.local.get('streamerName', function (StreamerName) {
        

        getData(`https://api.twitch.tv/helix/users?login=${StreamerName.streamerName}`).then((data) => {

            var profilePictureUrl = data.data[0].profile_image_url
            var channelDescription = data.data[0].description

            var picture = document.getElementById("profilePicture")
            picture.src = profilePictureUrl

            picture.onclick = function () {window.open(`https://www.twitch.tv/${StreamerName.streamerName}`)}

            document.getElementById("channelDescription").innerHTML = channelDescription
            document.getElementById("broadcasterType").innerHTML = `${data.data[0].broadcaster_type.charAt(0).toUpperCase() + data.data[0].broadcaster_type.slice(1)}`

            getData(`https://api.twitch.tv/helix/channels/followers?broadcaster_id=${data.data[0].id}&first=1`).then((followerData) => {
                
                document.getElementById("followerCount").innerHTML = `${followerData.total >= 1000000 ? `${(followerData.total/1000000).toFixed(2)} Mio.`:followerData.total.toLocaleString("en-US")} Follower`
                document.getElementById("loadingPlaceholder").remove()
                })
                
            

            var liveIndicator = document.getElementById("liveIndicator");
            var viewerCount = document.getElementById("viewerCount");
            var onlineState = document.getElementById("gameName"); 
            var streamStartedAt = document.getElementById("streamStartedAt");

            getData(`https://api.twitch.tv/helix/streams?user_id=${data.data[0].id}`).then((streamData) => {
                if(streamData.data != ""){
                
                    var date = new Date(streamData.data[0].started_at)
                    liveIndicator.style.opacity = 1
                    viewerCount.innerHTML = `${streamData.data[0].viewer_count} viewer`
                    onlineState.innerHTML = `${streamData.data[0].game_name}`
                    streamStartedAt.innerHTML =  `stream started at: ${date.getDate().toString().padStart(2, '0')}.${(date.getMonth() + 1).toString().padStart(2, '0')}.${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`

                    
                    
                }
                    
                
            })
        })
        document.getElementById("headline").innerHTML = `${StreamerName.streamerName}`;
    }); 
  };