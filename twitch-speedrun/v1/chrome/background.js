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
console.log(access_token)
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


function renderData(data) {
  return data
}


function checkStreamStatus() {
  chrome.storage.local.get('streamer', function (streamer) {
    if (!streamer.streamer) {
      return;
    }

    streamer.streamer.map((streamerContainer, index) => {

      streamer.streamer[index].key = index;

      chrome.storage.local.set(streamer);
    });

    try {
      streamer.streamer.map((streamerContainer) => {
        if (streamerContainer.notification) {
          getData(`https://api.twitch.tv/helix/streams?user_login=${streamerContainer.name}&type=live`).then((Data) => {

            console.log(Data)
        
            if (Data.status === "400") {
              streamer.streamer.splice(streamerContainer.key, 1);
              chrome.storage.local.set(streamer);
            }

            try {
              if (Data.data[0].id && streamerContainer.startedAt !== Data.data[0].started_at) {
                chrome.tabs.create({
                  url: `https://www.twitch.tv/${streamerContainer.name}`
                });
                
                streamer.streamer[streamerContainer.key].startedAt = Data.data[0].started_at;
              }
            } catch (TypeError) {
              console.log(TypeError);
            }

            chrome.storage.local.set(streamer);
          });
        }
      });
    } catch (TypeError) {
      console.log("empty list");
    }
  });
}

// send an alarm every 5 seconds
chrome.alarms.create("checkStreamAlarm", { periodInMinutes: 0.0833 });

// Listen for the alarm and execute the function
chrome.alarms.onAlarm.addListener((alarm) => {
  if (alarm.name === "checkStreamAlarm") {
    checkStreamStatus();
  }
});


chrome.runtime.onMessage.addListener(function (message, sender, senderResponse) {
  if (message.type === "fetch") {
    fetch(message.url).then(res => {
      return res.text()
    }).then(res => {
      senderResponse(res);
    })
  }
  return true
});


