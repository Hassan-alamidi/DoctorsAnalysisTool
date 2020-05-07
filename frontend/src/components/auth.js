const key = "isLoggedIn";
const isAdminKey = "isAdmin";
const doctorName = "firstName"

class Auth {
    constructor(){
        this.firstName = sessionStorage.getItem(doctorName)
        this.stored = sessionStorage.getItem(key)
        //this does nothing other than make the register staff button appear, the endpoint is secured to disallow non-admins from carrying out the action
        this.isAdminStored = sessionStorage.getItem(isAdminKey)
        if(typeof this.stored === 'undefined' || this.stored === null){
            this.authenticated = false;
            this.manager = false;
        }else{
            //convert the string to boolean and store the boolean in authenticated
            this.authenticated = (this.stored === "true");
            this.manager = (this.isAdminStored === "true");
        }
    }

    isAuthenticated(){
        return this.authenticated;
    }

    isAdmin(){
        return this.manager;
    }

    getName(){
        return this.firstName;
    }

    login(privilegeLevel, name){
        const admin = privilegeLevel === "Admin" ? true : false;
        this.authenticated = true;
        this.manager = admin;
        sessionStorage.setItem(doctorName, name)
        sessionStorage.setItem(key,"true");
        sessionStorage.setItem(isAdminKey, admin)
    }

    logout(){
        this.authenticated = false;
        sessionStorage.removeItem(key)
        sessionStorage.removeItem(isAdminKey);
        sessionStorage.removeItem(doctorName);
    }

}

export default new Auth();