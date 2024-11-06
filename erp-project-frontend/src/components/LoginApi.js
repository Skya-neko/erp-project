import axios from "axios";

export const execLogin = async (username, password) => {
    const url = `${process.env.REACT_APP_AUTH_API_URL}`;

    const header = {
        "Content-Type": "application/json",
    };

    const data = {
        clientId: username,
        clientSecret: password
    };

    return await axios({
        method: "POST",
        url: url,
        headers: header,
        data: data,
    })
        .then((res) => {
            if (res.data.code === "00") {
                console.log(`res: ${JSON.stringify(res)}`);
                return res.data;
            }
        })
        .catch((err) => {
            console.log(err);
        })
        .finally(() => {
            console.log("the http request is over");

        })
        ;
};
