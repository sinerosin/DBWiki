package com.example.dbwiki.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dbwiki.data.model.Charactermodel;
import com.example.dbwiki.data.model.CharacterResponse;
import com.example.dbwiki.data.remote.Resource;
import com.example.dbwiki.data.repository.CharacterRepository;

import java.util.List;

public class CharacterViewModel extends ViewModel {
    private final CharacterRepository repository;

    public MutableLiveData<Resource<Charactermodel>> informacionCharacter = new MutableLiveData<>();
    public MutableLiveData<Resource<List<CharacterResponse.CharacterEntry>>> characterList= new MutableLiveData<>();
    public CharacterViewModel() {
        repository = new CharacterRepository();
    }

    public void buscarCharacter(String name) {

        repository.getCharacter(name, new CharacterRepository.CharacterCallback() {
            @Override
            public void onResult(Resource<Charactermodel> result) {
                informacionCharacter.postValue(result);
            }
        });
    }
    public void cargarCharacterList() {
        repository.getCharacterList(result ->{
            characterList.postValue(result);
        });
    }
}