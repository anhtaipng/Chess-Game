import React, {useState} from 'react';
import styles from './form.module.css';
import {useDispatch, useSelector} from "react-redux";
import {loginUser, UserConstant} from "../../slices/user/userSlice";
import Loader from 'react-loader-spinner';
import DismissableAlert from "../alert/DismissableAlert";

const LoginForm = props => {
    const dispatch = useDispatch();
    const [username, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const logginState = useSelector(state => state.user.login_status);
    const handleSubmit = (e) =>{
        e.preventDefault();
        try{
            const requestResult = dispatch(loginUser({username, password})).unwrap();
            const testRealResult = async () => await requestResult;
            console.log("Login Result:",testRealResult().token);
        }
        catch (error){
            console.log("ERROR at Register asd:",error);
        }
    }
    return (
        <section className={styles.section}>
            <h3>Login Information</h3>
            <form className={styles.form} onSubmit={handleSubmit}>
                <label className={styles.label}>
                    <span className={styles.fromSpan}>Email</span>
                    <input  className={styles.formInput}
                        type="text"
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
                           onChange={(e) => setPassword(e.target.value)}/>
                </label>
                <button className={styles.formButton}>LOGIN</button>
            </form>
            {(logginState === UserConstant.LOGGING_IN_LOADING) && < Loader type="Circles" color="#00BFFF" height={80} width={80}/>}
            {(logginState === UserConstant.LOGGED_IN_FAILED) &&
                <DismissableAlert heading={`Failed to Login`} message={`Wrong username or password`} type={`danger`}/>
            }
            {(logginState === UserConstant.LOGGED_IN) &&
                <DismissableAlert heading={`Login Succesfully`} message={`Now go play your game`} type={`success`}/>
            }
        </section>
    );
};


export default LoginForm;

