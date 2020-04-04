const key = "isLoggedIn";

class Auth {
    constructor(){
        this.stored = sessionStorage.getItem(key)
        if(typeof this.stored === 'undefined' || this.stored === null){
            this.authenticated = false;
        }else{
            //convert the string to boolean and store the boolean in authenticated
            this.authenticated = (this.stored == 'true');
        }
    }

    isAuthenticated(){
        return this.authenticated;
    }

    login(){
        this.authenticated = true;
        sessionStorage.setItem(key,"true");
    }

    logout(){
        this.authenticated = false;
        sessionStorage.removeItem(key)
        //sessionStorage.setItem(key,"false");
    }

}

export default new Auth();