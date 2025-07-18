package utn.frc.backend.tpi.logistica.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frc.backend.tpi.logistica.dtos.TramoRutaDto;
import utn.frc.backend.tpi.logistica.models.Solicitud;
import utn.frc.backend.tpi.logistica.models.TramoRuta;
import utn.frc.backend.tpi.logistica.repositories.TramoRutaRepository;

@Service
public class TramoRutaService {

    @Autowired
    private TramoRutaRepository tramoRutaRepo;

    @Autowired
    private GeoService geoService;

    public List<TramoRuta> obtenerTodos() {
        return tramoRutaRepo.findAll();
    }

    public TramoRuta obtenerPorId(Long id) {
        return tramoRutaRepo.findById(id).orElse(null);
    }

    public TramoRuta crear(TramoRuta tramoRuta) {
        return tramoRutaRepo.save(tramoRuta);
    }

    public TramoRuta actualizar(Long id, TramoRuta tramoRuta) {
        tramoRuta.setId(id);
        return tramoRutaRepo.save(tramoRuta);
    }

    public void eliminar(Long id) {
        tramoRutaRepo.deleteById(id);
    }

    public List<TramoRuta> generarTramos(Solicitud solicitud) {
        
        List<TramoRuta> tramos = new ArrayList<>();

        try {
            
            if (solicitud.getDepositoId() != null) {
            
            // Tramo Ciudad → Depósito

            TramoRutaDto tramo1Dto = geoService.calcularDistanciaCiudadADeposito(
            solicitud.getCiudadOrigenId(), solicitud.getDepositoId());

            TramoRuta tramo1 = new TramoRuta();
            tramo1.setOrden(1);
            tramo1.setUbicacionOrigenId(solicitud.getCiudadOrigenId());
            tramo1.setUbicacionDestinoId(solicitud.getDepositoId());
            tramo1.setDistancia(tramo1Dto.getDistancia());
            tramo1.setTiempoEstimado(tramo1Dto.getTiempoEstimado());
            tramo1.setSolicitud(solicitud);
            tramos.add(tramo1);

            // Tramo Depósito → Ciudad destino
            
            TramoRutaDto tramo2Dto = geoService.calcularDistanciaDepositoACiudad(
            solicitud.getDepositoId(), solicitud.getCiudadDestinoId());

            TramoRuta tramo2 = new TramoRuta();
            tramo2.setOrden(2);
            tramo2.setUbicacionOrigenId(solicitud.getDepositoId());
            tramo2.setUbicacionDestinoId(solicitud.getCiudadDestinoId());
            tramo2.setDistancia(tramo2Dto.getDistancia());
            tramo2.setTiempoEstimado(tramo2Dto.getTiempoEstimado());
            tramo2.setSolicitud(solicitud);
            tramos.add(tramo2);
            
        } else {
            // Tramo único Ciudad → Ciudad
            TramoRutaDto tramoDto = geoService.calcularDistanciaEntreCiudades(
            solicitud.getCiudadOrigenId(), solicitud.getCiudadDestinoId());

            TramoRuta tramo = new TramoRuta();
            tramo.setOrden(1);
            tramo.setUbicacionOrigenId(solicitud.getCiudadOrigenId());
            tramo.setUbicacionDestinoId(solicitud.getCiudadDestinoId());
            tramo.setDistancia(tramoDto.getDistancia());
            tramo.setTiempoEstimado(tramoDto.getTiempoEstimado());
            tramo.setSolicitud(solicitud);
            tramos.add(tramo);
            }
        
            return tramos;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al calcular tramos" + e.getMessage());
        }

    }

}
