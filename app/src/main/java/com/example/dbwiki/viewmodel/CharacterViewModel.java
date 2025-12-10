package com.example.dbwiki.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dbwiki.data.model.Charactermodel; // Importar Charactermodel
import com.example.dbwiki.data.model.CharacterResponse;
import com.example.dbwiki.data.remote.Resource;
import com.example.dbwiki.data.repository.CharacterRepository;

import java.util.List;

public class CharacterViewModel extends ViewModel {
    private final CharacterRepository repository;

    // LiveData observado por el Fragment.
    // Contendrá Loading, Success o Error, junto al dato correspondiente.
    public MutableLiveData<Resource<Charactermodel>> informacionCharacter = new MutableLiveData<>();
    public MutableLiveData<Resource<List<CharacterResponse.CharacterEntry>>> characterList= new MutableLiveData<>();
    public CharacterViewModel() {
        // Inicializamos el Repository, capa encargada de hablar con la API
        repository = new CharacterRepository();
    }

    // Método usado por el Fragment para iniciar una búsqueda
    public void buscarCharacter(String name) {

        // Realizamos la búsqueda mediante el Repository
        // y recibimos la respuesta a través del callback
        repository.getCharacter(name, new CharacterRepository.CharacterCallback() {
            @Override
            public void onResult(Resource<Charactermodel> result) {
                // Publicamos el resultado dentro del LiveData
                informacionCharacter.postValue(result);
            }
        });
    }
    public void cargarCharacterList() {
        // Lanzamos la petición
        repository.getCharacterList(result ->{
            characterList.postValue(result);
        });
    }
}