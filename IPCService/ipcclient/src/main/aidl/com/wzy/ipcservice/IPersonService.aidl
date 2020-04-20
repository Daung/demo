// IPersonService.aidl
package com.wzy.ipcservice;

import com.wzy.ipcservice.Person;

parcelable Person;

interface IPersonService {

    void addPerson(in Person person);
    List<Person> getPersonList();
}
