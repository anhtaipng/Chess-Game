import React, {useReducer, useState} from 'react';
import styles from './form.module.css';
import {useDispatch, useSelector} from "react-redux";
import { registerUser, UserConstant} from "../../slices/user/userSlice";
import Loader from 'react-loader-spinner';
import DismissableAlert from "../alert/DismissableAlert";

const REGISTER_ERROR_CONSTANT = {
    ILL_FORMED: "ILL_FORM_INFO",
    NO_ERROR: "NO_ERROR",
    PASSWORD_DONT_MATCH: "PASSWORD_DONT_MATCH"
}
const initialState = REGISTER_ERROR_CONSTANT.NO_ERROR;
const reducer = (state,action) => {
    switch (action.type){
        case 'ill_formed_username':
            return REGISTER_ERROR_CONSTANT.ILL_FORMED;
        case 'password_no_match':
            return REGISTER_ERROR_CONSTANT.PASSWORD_DONT_MATCH;
        case 'validate_ok':
            return REGISTER_ERROR_CONSTANT.NO_ERROR;
        default:
            return REGISTER_ERROR_CONSTANT.NO_ERROR;
    }
}
const RegisterForm =  (props) => {
    const baseUrl = `{serverRoot}/register/`;
    const dispatchToStore = useDispatch();
    const loginState = useSelector(state => state.user.login_status);
    const [username, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [regErrState, dispatch] = useReducer(reducer, initialState);

    const handleSubmit = async (e) =>{
        e.preventDefault();
        if(password !== confirmPassword) {
            dispatch({type: "password_no_match"})
        }
        else if (username !== username.trim()) {
            dispatch({type: "ill_formed_username"})
        }
        dispatch('validate_ok');
        // If validate ok
        try{
            const requestResult = await dispatchToStore(registerUser({username, password})).unwrap();
            if(!requestResult){
                dispatch(registerUser.rejected);
            }
            console.log("regResult:",requestResult);
        }
        catch (error){
            console.log("ERROR at Register:",error);
        }

    }
    return (
        <section className={styles.section}>
            <h3>Login Information</h3>
            <form className={styles.form} onSubmit={handleSubmit}>
                <label className={styles.label}>
                    <span className={styles.fromSpan}>Your Username or Email</span>
                    <input  className={styles.formInput}
                            type="email"
                            defaultValue=""
                            placeholder="Your account username or email"
                            required
                            onChange={(e) => setUserName(e.target.value)}/>
                </label>
                <label className={styles.label}>
                    <span className={styles.fromSpan}>Password</span>
                    <input  className={styles.formInput} type="password"
                            defaultValue=""
                            required
                            placeholder="Enter your password"
                            onChange={(e) => setPassword(e.target.value)}/>
                </label>
                <label className={styles.label}>
                    <span className={styles.fromSpan}>Confirm Password</span>
                    <input  className={styles.formInput} type="password"
                            defaultValue=""
                            placeholder="Enter your password again. Make sure it matches"
                            required
                            onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </label>
                <button className={styles.formButton}>LOGIN</button>
            </form>
            {(loginState === UserConstant.REGISTERING_LOADING) && < Loader type="Circles" color="#FFFFFF" height={80} width={80}/>}
            {(loginState === UserConstant.REGISTERING_FAILED) &&
            <DismissableAlert heading={`Failed to Login`} message={`Wrong username or password`} type={`danger`}/>
            }
            {(loginState === UserConstant.REGISTERING_SUCCESS) &&
            <DismissableAlert heading={`Register Succesfully`} message={`Now go play your game`} type={`success`}/>
            }
            {(regErrState === REGISTER_ERROR_CONSTANT.PASSWORD_DONT_MATCH &&<DismissableAlert heading={`Invalid Password Validation`} message={`Password and Confirm doesn't match`} type={`warning`}/>)}
            {(regErrState === REGISTER_ERROR_CONSTANT.ILL_FORMED &&<DismissableAlert heading={`Invalid Username`} message={`The username is illformed. Please Check again.` } type ={`warning`}/>)}
        </section>
    );
};


export default RegisterForm;

